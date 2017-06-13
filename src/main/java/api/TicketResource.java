package api;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.EmptyShoppingListException;
import api.exceptions.InvalidProductAmountInNewTicketException;
import api.exceptions.InvalidProductAmountInUpdateTicketException;
import api.exceptions.InvalidProductDiscountException;
import api.exceptions.MalformedDateException;
import api.exceptions.NotEnoughStockException;
import api.exceptions.NotFoundProductCodeException;
import api.exceptions.NotFoundProductCodeInTicketException;
import api.exceptions.NotFoundTicketReferenceException;
import api.exceptions.NotFoundUserMobileException;
import api.exceptions.VoucherAlreadyConsumedException;
import api.exceptions.VoucherHasExpiredException;
import api.exceptions.VoucherNotFoundException;
import controllers.ArticleController;
import controllers.CashierClosuresController;
import controllers.ProductController;
import controllers.TicketController;
import controllers.UserController;
import controllers.VoucherController;
import entities.core.Article;
import entities.core.Product;
import entities.core.Shopping;
import entities.core.Ticket;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import wrappers.DayTicketWrapper;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.ShoppingUpdateWrapper;
import wrappers.TicketCreationResponseWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketReferenceCreatedWrapper;
import wrappers.TicketReferenceWrapper;
import wrappers.TicketUpdateWrapper;
import wrappers.TicketUserPatchBodyWrapper;
import wrappers.TicketWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.TICKETS)
public class TicketResource {

    private static final int MIN_PRODUCT_DISCOUNT = 0;

    private static final int MAX_PRODUCT_DISCOUNT = 100;

    private TicketController ticketController;

    private UserController userController;

    private ProductController productController;

    private ArticleController articleController;

    private VoucherController voucherController;

    private CashierClosuresController cashierClosuresController;

    @Autowired
    public void setTicketController(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @Autowired
    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    @Autowired
    public void setArticleController(ArticleController articleController) {
        this.articleController = articleController;
    }

    @Autowired
    public void setVoucherController(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Autowired
    public void setCashierClosuresController(CashierClosuresController cashierClosuresController) {
        this.cashierClosuresController = cashierClosuresController;
    }

    // @PreAuthorize("hasRole('ADMIN')or hasRole('MANAGER') or hasRole('OPERATOR')")
    @RequestMapping(method = RequestMethod.POST)
    public TicketCreationResponseWrapper createTicket(@RequestBody TicketCreationWrapper ticketCreationWrapper)
            throws EmptyShoppingListException, NotFoundProductCodeException, NotFoundUserMobileException, NotEnoughStockException,
            InvalidProductAmountInNewTicketException, InvalidProductDiscountException, IOException, VoucherNotFoundException,
            VoucherHasExpiredException, VoucherAlreadyConsumedException {

        Long userMobile = ticketCreationWrapper.getUserMobile();
        if (userMobile != null && !userController.userExists(userMobile)) {
            throw new NotFoundUserMobileException();
        }

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = ticketCreationWrapper.getShoppingList();
        if (shoppingCreationWrapperList.isEmpty()) {
            throw new EmptyShoppingListException();
        }

        List<String> voucherReferences = ticketCreationWrapper.getVouchers();
        checkVouchers(voucherReferences);

        for (ShoppingCreationWrapper shoppingCreationWrapper : shoppingCreationWrapperList) {
            String productCode = shoppingCreationWrapper.getProductCode();
            if (!productController.productCodeExists(productCode)) {
                throw new NotFoundProductCodeException("Product code: " + productCode);
            }
            if (shoppingCreationWrapper.getAmount() <= 0) {
                Product product = productController.getProductByCode(productCode);
                throw new InvalidProductAmountInNewTicketException(
                        "Product: '" + product.getDescription() + "'. Product code: " + productCode);
            }
            if (shoppingCreationWrapper.getDiscount() < MIN_PRODUCT_DISCOUNT
                    || shoppingCreationWrapper.getDiscount() > MAX_PRODUCT_DISCOUNT) {
                Product product = productController.getProductByCode(productCode);
                throw new InvalidProductDiscountException("Product: '" + product.getDescription() + "'. Product code: " + productCode);
            }
            // If the product is an article, check if there is enough stock and update it
            if (articleController.articleCodeExists(productCode)) {
                if (!articleController.hasEnoughStock(productCode, shoppingCreationWrapper.getAmount())) {
                    Article article = articleController.getArticleByCode(productCode);
                    throw new NotEnoughStockException("Article: '" + article.getDescription() + "'. Current stock: " + article.getStock()
                            + ". Article code: " + productCode);
                }
                articleController.consumeArticle(productCode, shoppingCreationWrapper.getAmount());
            }
        }

        // Consume vouchers and add given cash to cashierClosure
        for (String voucherReference : voucherReferences) {
            voucherController.consumeVoucher(voucherReference);
        }
        cashierClosuresController.depositCashierRequest(ticketCreationWrapper.getCash());

        return ticketController.createTicket(ticketCreationWrapper);
    }

    @RequestMapping(value = Uris.REFERENCE, method = RequestMethod.PATCH)
    public TicketReferenceWrapper updateTicket(@PathVariable String reference, @RequestBody TicketUpdateWrapper ticketUpdateWrapper)
            throws NotFoundTicketReferenceException, NotFoundProductCodeInTicketException, InvalidProductAmountInUpdateTicketException,
            NotEnoughStockException, VoucherNotFoundException, VoucherHasExpiredException, VoucherAlreadyConsumedException {

        checkTicketReferenceExists(reference);

        List<String> voucherReferences = ticketUpdateWrapper.getVouchers();
        checkVouchers(voucherReferences);

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = ticketUpdateWrapper.getShoppingUpdateList();
        Iterator<Shopping> shoppingList = ticketController.getTicket(reference).getShoppingList().iterator();
        for (ShoppingUpdateWrapper shoppingUpdateWrapper : shoppingUpdateWrapperList) {
            String productCode = shoppingUpdateWrapper.getProductCode();
            Shopping shoppingInTicket = null;
            while (shoppingList.hasNext() && (shoppingInTicket == null)) {
                Shopping shopping = shoppingList.next();
                if (shopping.getProduct().getCode().equals(productCode)) {
                    shoppingInTicket = shopping;
                }
            }
            if (shoppingInTicket == null) {
                throw new NotFoundProductCodeInTicketException("Product code: " + productCode);
            }

            if (shoppingUpdateWrapper.getAmount() < 0) {
                Product product = productController.getProductByCode(productCode);
                throw new InvalidProductAmountInUpdateTicketException(
                        "Product: '" + product.getDescription() + "'. Product code: " + productCode);
            }

            // If the product is an article, check if there is enough stock and update it
            if (articleController.articleCodeExists(productCode)) {
                int stockDifference = shoppingUpdateWrapper.getAmount() - shoppingInTicket.getAmount();
                if (!articleController.hasEnoughStock(productCode, stockDifference)) {
                    Article article = articleController.getArticleByCode(productCode);
                    throw new NotEnoughStockException("Article: '" + article.getDescription() + "'. Current stock: " + article.getStock()
                            + ". Article code: " + productCode);
                }
                articleController.consumeArticle(productCode, stockDifference);
            }
        }

        // Consume vouchers and add given cash to cashierClosure
        for (String voucherReference : voucherReferences) {
            voucherController.consumeVoucher(voucherReference);
        }
        cashierClosuresController.depositCashierRequest(ticketUpdateWrapper.getCash());

        Ticket ticket = ticketController.updateTicket(reference, shoppingUpdateWrapperList);
        return new TicketReferenceWrapper(ticket.getReference());
    }

    @RequestMapping(value = Uris.REFERENCE, method = RequestMethod.GET)
    public TicketWrapper getTicket(@PathVariable String reference) throws NotFoundTicketReferenceException {
        checkTicketReferenceExists(reference);

        return new TicketWrapper(ticketController.getTicket(reference));
    }

    @RequestMapping(value = Uris.DAY_TICKETS + Uris.DATE, method = RequestMethod.GET)
    public List<DayTicketWrapper> getWholeDayTickets(@PathVariable String date) throws MalformedDateException {
        String dateFormat = Constants.US_DATE_FORMAT;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
        if (!date.matches(Constants.US_DATE_REGEX)) {
            throw new MalformedDateException(dateFormat, "Date sent: " + date);
        }
        Calendar dayToGetTickets = Calendar.getInstance();
        try {
            dayToGetTickets.setTime(dateFormatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ticketController.getWholeDayTickets(dayToGetTickets);
    }

    @RequestMapping(value = Uris.TRACKING + Uris.REFERENCE, method = RequestMethod.GET)
    public List<ShoppingTrackingWrapper> getTicketTracking(@PathVariable String reference) throws NotFoundTicketReferenceException {
        checkTicketReferenceExists(reference);

        return ticketController.getTicketTracking(reference);
    }

    @ApiOperation(value = "Find partners")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported.")
    })
    @RequestMapping(method = RequestMethod.GET)
    // @PreAuthorize("hasRole('ADMIN')or hasRole('MANAGER') or hasRole('OPERATOR')")
    public Page<TicketReferenceCreatedWrapper> getTicketsByUserMobile(@RequestParam long mobile, Pageable pageable) {
        return ticketController.getTicketsByUserMobile(mobile, pageable);
    }

    private void checkTicketReferenceExists(String reference) throws NotFoundTicketReferenceException {
        if (!ticketController.ticketReferenceExists(reference)) {
            throw new NotFoundTicketReferenceException("Ticket reference: " + reference);
        }
    }

    
    @RequestMapping(value = Uris.ALL, method = RequestMethod.GET)
    public List<Ticket> listTickets() {
        return ticketController.findAll();
    }


    private void checkVouchers(List<String> voucherReferenceList)
            throws VoucherNotFoundException, VoucherHasExpiredException, VoucherAlreadyConsumedException {

        for (String reference : voucherReferenceList) {
            if (!voucherController.voucherExists(reference)) {
                throw new VoucherNotFoundException("Voucher reference: " + reference);
            }
            if (voucherController.voucherHasExpired(reference)) {
                throw new VoucherHasExpiredException("Voucher reference: " + reference);
            }
            if (voucherController.isVoucherConsumed(reference)) {
                throw new VoucherAlreadyConsumedException("Voucher reference: " + reference);
            }
        }
    }
    
    private void throwExceptionIfUserDoesNotExist(Long userMobile) throws NotFoundUserMobileException {
        if(!userController.userExists(userMobile)){
            throw new NotFoundUserMobileException("User mobile: " + userMobile);
        }
    }

    @RequestMapping(value = Uris.REFERENCE + Uris.TICKET_USER, method = RequestMethod.PATCH)
    public TicketWrapper associateUserToTicket(@PathVariable String reference,
            @RequestBody TicketUserPatchBodyWrapper ticketUserPatchWrapper) throws NotFoundTicketReferenceException, NotFoundUserMobileException {
        checkTicketReferenceExists(reference);
        throwExceptionIfUserDoesNotExist(ticketUserPatchWrapper.getUserMobile());
        return ticketController.associateUserToTicket(reference, ticketUserPatchWrapper.getUserMobile());
    }
}

package api;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.EmptyShoppingListException;
import api.exceptions.InvalidProductAmountInNewTicketException;
import api.exceptions.InvalidProductAmountInUpdateTicketException;
import api.exceptions.InvalidProductDiscountException;
import api.exceptions.NotEnoughStockException;
import api.exceptions.NotFoundProductCodeException;
import api.exceptions.NotFoundProductCodeInTicketException;
import api.exceptions.NotFoundTicketReferenceException;
import api.exceptions.NotFoundUserMobileException;
import controllers.ArticleController;
import controllers.ProductController;
import controllers.TicketController;
import controllers.UserController;
import entities.core.Shopping;
import entities.core.Ticket;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.ShoppingUpdateWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketReferenceWrapper;
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

    // @PreAuthorize("hasRole('ADMIN')or hasRole('MANAGER') or hasRole('OPERATOR')")
    @RequestMapping(method = RequestMethod.POST)
    public TicketReferenceWrapper createTicket(@RequestBody TicketCreationWrapper ticketCreationWrapper)
            throws EmptyShoppingListException, NotFoundProductCodeException, NotFoundUserMobileException, NotEnoughStockException,
            InvalidProductAmountInNewTicketException, InvalidProductDiscountException {
        Long userMobile = ticketCreationWrapper.getUserMobile();
        if (userMobile != null && !userController.userMobileExists(userMobile)) {
            throw new NotFoundUserMobileException();
        }

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = ticketCreationWrapper.getShoppingList();
        if (shoppingCreationWrapperList.isEmpty()) {
            throw new EmptyShoppingListException();
        }

        for (ShoppingCreationWrapper shoppingCreationWrapper : shoppingCreationWrapperList) {
            String productCode = shoppingCreationWrapper.getProductCode();
            if (!productController.productCodeExists(productCode)) {
                throw new NotFoundProductCodeException("Product code: " + productCode);
            }
            if (shoppingCreationWrapper.getAmount() <= 0) {
                throw new InvalidProductAmountInNewTicketException("Product code: " + productCode);
            }
            if (shoppingCreationWrapper.getDiscount() < MIN_PRODUCT_DISCOUNT
                    || shoppingCreationWrapper.getDiscount() > MAX_PRODUCT_DISCOUNT) {
                throw new InvalidProductDiscountException("Product code: " + productCode);
            }
            // If the product is an article, check if there is enough stock and update it
            if (articleController.articleCodeExists(productCode)) {
                if (!articleController.hasEnoughStock(productCode, shoppingCreationWrapper.getAmount())) {
                    throw new NotEnoughStockException("Article code: " + productCode);
                }
                articleController.consumeArticle(productCode, shoppingCreationWrapper.getAmount());
            }
        }

        Ticket ticket = ticketController.createTicket(ticketCreationWrapper);
        return new TicketReferenceWrapper(ticket.getReference());
    }

    @RequestMapping(value = Uris.REFERENCE, method = RequestMethod.PUT)
    public TicketReferenceWrapper updateTicket(@PathVariable String reference,
            @RequestBody List<ShoppingUpdateWrapper> shoppingUpdateWrapperList) throws NotFoundTicketReferenceException,
            NotFoundProductCodeInTicketException, InvalidProductAmountInUpdateTicketException, NotEnoughStockException {
        if (!ticketController.ticketReferenceExists(reference)) {
            throw new NotFoundTicketReferenceException("Ticket reference: " + reference);
        }

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
                throw new InvalidProductAmountInUpdateTicketException("Product code: " + productCode);
            }

            // If the product is an article, check if there is enough stock and update it
            if (articleController.articleCodeExists(productCode)) {
                int stockDifference = shoppingUpdateWrapper.getAmount() - shoppingInTicket.getAmount();
                if (!articleController.hasEnoughStock(productCode, stockDifference)) {
                    throw new NotEnoughStockException("Article code: " + productCode);
                }
                articleController.consumeArticle(productCode, stockDifference);
            }
        }

        Ticket ticket = ticketController.updateTicket(reference, shoppingUpdateWrapperList);
        return new TicketReferenceWrapper(ticket.getReference());
    }

    @RequestMapping(value = Uris.REFERENCE, method = RequestMethod.GET)
    public TicketWrapper getTicket(@PathVariable String reference) throws NotFoundTicketReferenceException {
        if (!ticketController.ticketReferenceExists(reference)) {
            throw new NotFoundTicketReferenceException("Ticket reference: " + reference);
        }
        return new TicketWrapper(ticketController.getTicket(reference));
    }

    @RequestMapping(value = Uris.TRACKING + Uris.REFERENCE, method = RequestMethod.GET)
    public List<ShoppingTrackingWrapper> getTicketTracking(@PathVariable String reference) throws NotFoundTicketReferenceException {
        if (!ticketController.ticketReferenceExists(reference)) {
            throw new NotFoundTicketReferenceException("Ticket reference: " + reference);
        }

        return ticketController.getTicketTracking(reference);
    }
}

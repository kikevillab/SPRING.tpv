package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.EmptyShoppingListException;
import api.exceptions.NotEnoughStockException;
import api.exceptions.NotFoundProductCodeException;
import api.exceptions.NotFoundTicketReferenceException;
import api.exceptions.NotFoundUserMobileException;
import controllers.ArticleController;
import controllers.ProductController;
import controllers.TicketController;
import controllers.UserController;
import entities.core.Ticket;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketReferenceWrapper;
import wrappers.TicketWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.TICKETS)
public class TicketResource {

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
            throws EmptyShoppingListException, NotFoundProductCodeException, NotFoundUserMobileException, NotEnoughStockException {
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
            if (articleController.articleCodeExists(productCode)) {
                if (articleController.hasEnoughStock(productCode, shoppingCreationWrapper.getAmount())) {
                    articleController.consumeArticle(productCode, shoppingCreationWrapper.getAmount());
                } else {
                    throw new NotEnoughStockException("Article code: " + productCode);
                }
            }
        }

        Ticket ticket = ticketController.createTicket(ticketCreationWrapper);
        return new TicketReferenceWrapper(ticket.getReference());
    }

    @RequestMapping(value = Uris.REFERENCE, method = RequestMethod.GET)
    public TicketWrapper getTicket(@PathVariable String reference) throws NotFoundTicketReferenceException {
        if (!ticketController.ticketReferenceExists(reference)) {
            throw new NotFoundTicketReferenceException("Ticket reference: " + reference);
        }
        return ticketController.getTicket(reference);
    }

    @RequestMapping(value = Uris.TRACKING + Uris.REFERENCE, method = RequestMethod.GET)
    public List<ShoppingTrackingWrapper> getTicketTracking(@PathVariable String reference) throws NotFoundTicketReferenceException {
        if (!ticketController.ticketReferenceExists(reference)) {
            throw new NotFoundTicketReferenceException("Ticket reference: " + reference);
        }

        return ticketController.getTicketTracking(reference);
    }
}

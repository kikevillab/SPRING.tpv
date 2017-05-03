package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.EmptyShoppingListException;
import api.exceptions.NotFoundProductIdException;
import api.exceptions.NotFoundUserIdException;
import controllers.ProductController;
import controllers.TicketController;
import controllers.UserController;
import entities.core.Ticket;
import wrappers.ShoppingCreationWrapper;
import wrappers.TicketCreationWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.TICKETS)
public class TicketResource {

    private TicketController ticketController;

    private UserController userController;

    private ProductController productController;

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

    // @PreAuthorize("hasRole('ADMIN')or hasRole('MANAGER') or hasRole('OPERATOR')")
    @RequestMapping(method = RequestMethod.POST)
    public Ticket login(@RequestBody TicketCreationWrapper ticketCreationWrapper)
            throws NotFoundUserIdException, EmptyShoppingListException, NotFoundProductIdException {
        Integer userId = ticketCreationWrapper.getUserId();
        if (userId != null && !userController.userExists(userId)) {
            throw new NotFoundUserIdException();
        }

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = ticketCreationWrapper.getShoppingList();
        if (shoppingCreationWrapperList.isEmpty()) {
            throw new EmptyShoppingListException();
        }

        for (ShoppingCreationWrapper shoppingCreationWrapper : shoppingCreationWrapperList) {
            if (!productController.productExists(shoppingCreationWrapper.getProductId())) {
                throw new NotFoundProductIdException("Product id: " + shoppingCreationWrapper.getProductId());
            }
        }

        return ticketController.createTicket(ticketCreationWrapper);
    }

}

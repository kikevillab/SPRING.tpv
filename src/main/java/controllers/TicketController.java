package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.ProductDao;
import daos.core.TicketDao;
import daos.users.UserDao;
import entities.core.Product;
import entities.core.Shopping;
import entities.core.ShoppingState;
import entities.core.Ticket;
import entities.users.User;
import wrappers.ShoppingCreationWrapper;
import wrappers.TicketCreationWrapper;

@Controller
public class TicketController {

    private TicketDao ticketDao;

    private UserDao userDao;

    private ProductDao ProductDao;

    @Autowired
    public void setProductDao(TicketDao productDao) {
        this.ticketDao = productDao;
    }

    public Ticket createTicket(TicketCreationWrapper ticketCreationWrapper) {
        Ticket ticket = new Ticket(getNextId());
        Integer userId = ticketCreationWrapper.getUserId();
        if (userId != null) {
            User user = userDao.findOne(userId);
            ticket.setUser(user);
        }

        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingCreationWrapper shoppingCreationWrapper : ticketCreationWrapper.getShoppingList()) {
            long productId = shoppingCreationWrapper.getProductId();
            Product product = ProductDao.findOne(productId);
            ShoppingState shoppingState = shoppingCreationWrapper.isDelivered() ? ShoppingState.COMMITTED : ShoppingState.OPENED;
            Shopping shopping = new Shopping(shoppingCreationWrapper.getAmount(), shoppingCreationWrapper.getDiscount(), product,
                    product.getDescription(), product.getRetailPrice(), shoppingState);
            shoppingList.add(shopping);
        }
        
        ticket.setShoppingList(shoppingList);
        ticketDao.save(ticket);
        
        return ticket;
    }

    private long getNextId() {
        long nextId = 0;
        Ticket ticket = ticketDao.findFirstByOrderByCreatedDescIdDesc();

        if (ticket != null) {
            Calendar todayMidnight = Calendar.getInstance();
            todayMidnight.set(Calendar.HOUR_OF_DAY, 0);
            todayMidnight.set(Calendar.MINUTE, 0);
            todayMidnight.set(Calendar.SECOND, 0);
            todayMidnight.set(Calendar.MILLISECOND, 0);

            if (ticket.getCreated().compareTo(todayMidnight) >= 0) {
                nextId = ticket.getId() + 1;
            }
        }
        return nextId;
    }
}

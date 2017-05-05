package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.InvoiceDao;
import daos.core.ProductDao;
import daos.core.TicketDao;
import daos.users.UserDao;
import entities.core.Product;
import entities.core.Shopping;
import entities.core.ShoppingState;
import entities.core.Ticket;
import entities.users.User;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketIdWrapper;

@Controller
public class TicketController {

    private TicketDao ticketDao;

    private UserDao userDao;

    private ProductDao productDao;

    private InvoiceDao invoiceDao;

    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public Ticket createTicket(TicketCreationWrapper ticketCreationWrapper) {
        Ticket ticket = new Ticket(getNextId());
        Long userMobile = ticketCreationWrapper.getUserMobile();
        if (userMobile != null) {
            User user = userDao.findByMobile(userMobile);
            ticket.setUser(user);
        }

        List<Shopping> shoppingList = new ArrayList<>();
        for (ShoppingCreationWrapper shoppingCreationWrapper : ticketCreationWrapper.getShoppingList()) {
            String productCode = shoppingCreationWrapper.getProductCode();
            Product product = productDao.findFirstByCode(productCode);
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

    public List<ShoppingTrackingWrapper> getTicketTracking(String reference) {
        Ticket ticket = ticketDao.findFirstByReference(reference);

        List<ShoppingTrackingWrapper> shoppingTrackingWrapperList = new LinkedList<>();
        for (Shopping shopping : ticket.getShoppingList()) {
            shoppingTrackingWrapperList.add(new ShoppingTrackingWrapper(shopping));
        }

        return shoppingTrackingWrapperList;
    }

    public boolean ticketReferenceExists(String reference) {
        Ticket ticket = ticketDao.findFirstByReference(reference);
        return ticket != null;
    }

    public Ticket findOneTicket(TicketIdWrapper ticketIdWrapper) {
        return ticketDao.findOne(ticketIdWrapper.getId());
    }

    public boolean ticketIsAssignedToInvoice(Ticket ticket) {
        return invoiceDao.findByTicket(ticket) != null;
    }

    public boolean ticketIsClosed(Ticket ticket) {
        boolean closed = true;
        for (Shopping shopping : ticket.getShoppingList()) {
            if (shopping.getShoppingState() != ShoppingState.CLOSED) {
                closed = false;
            }
        }
        return closed;
    }

}

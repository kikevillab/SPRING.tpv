package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.core.TicketDao;
import entities.core.Shopping;
import entities.core.ShoppingState;
import entities.core.Ticket;
import wrappers.ShoppingCreationWrapper;
import wrappers.TicketCreationWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class TicketControllerIT {

    @Autowired
    private TicketController ticketController;

    @Autowired
    private TicketDao ticketDao;

    @Test
    public void testCreateTicketWithoutUser() {
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductId(84000001111L);
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        long lastTicketId = ticketDao.findFirstByOrderByCreatedDescIdDesc().getId();

        Ticket ticket = ticketController.createTicket(ticketCreationWrapper);
        List<Shopping> shoppingList = ticket.getShoppingList();
        Shopping shopping = shoppingList.get(0);

        assertEquals(lastTicketId + 1, ticket.getId());
        assertNull(ticket.getUser());
        assertNotNull(shoppingList);
        assertEquals(shoppingCreationWrapper.getProductId(), shopping.getProduct().getId());
        assertEquals(shoppingCreationWrapper.getAmount(), shopping.getAmount());
        assertEquals(shoppingCreationWrapper.getDiscount(), shopping.getDiscount());
        assertEquals(ShoppingState.COMMITTED, shopping.getShoppingState());

        ticketDao.delete(ticket);
    }

    @Test
    public void testCreateTicketWithUser() {
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        Long userMobile = 666000000L;
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductId(84000002222L);
        shoppingCreationWrapper.setAmount(1);
        shoppingCreationWrapper.setDiscount(5);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        long lastTicketId = ticketDao.findFirstByOrderByCreatedDescIdDesc().getId();

        Ticket ticket = ticketController.createTicket(ticketCreationWrapper);
        List<Shopping> shoppingList = ticket.getShoppingList();
        Shopping shopping = shoppingList.get(0);

        assertEquals(lastTicketId + 1, ticket.getId());
        assertNotNull(ticket.getUser());
        assertNotNull(shoppingList);
        assertEquals(shoppingCreationWrapper.getProductId(), shopping.getProduct().getId());
        assertEquals(shoppingCreationWrapper.getAmount(), shopping.getAmount());
        assertEquals(shoppingCreationWrapper.getDiscount(), shopping.getDiscount());
        assertEquals(ShoppingState.OPENED, shopping.getShoppingState());

        ticketDao.delete(ticket);
    }

}

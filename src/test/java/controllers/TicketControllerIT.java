package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import wrappers.ShoppingTrackingWrapper;
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
        shoppingCreationWrapper.setProductCode("embroidery0");
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
        assertEquals(shoppingCreationWrapper.getProductCode(), shopping.getProduct().getCode());
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
        shoppingCreationWrapper.setProductCode("embroidery0");
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
        assertEquals(shoppingCreationWrapper.getProductCode(), shopping.getProduct().getCode());
        assertEquals(shoppingCreationWrapper.getAmount(), shopping.getAmount());
        assertEquals(shoppingCreationWrapper.getDiscount(), shopping.getDiscount());
        assertEquals(ShoppingState.OPENED, shopping.getShoppingState());

        ticketDao.delete(ticket);
    }

    @Test
    public void testGetTicket() {
        List<Ticket> ticketList = ticketDao.findAll();
        Ticket ticket = ticketList.get(new Random().nextInt(ticketList.size()));

        Ticket ticketObtained = ticketController.getTicket(ticket.getReference());

        assertNotNull(ticketObtained);
        assertEquals(ticket.getId(), ticketObtained.getId());
        assertEquals(ticket.getCreated(), ticketObtained.getCreated());
        assertEquals(ticket.getReference(), ticketObtained.getReference());
        for (int i = 0; i < ticketObtained.getShoppingList().size(); i++) {
            Shopping shoppingObtained = ticketObtained.getShoppingList().get(i);
            Shopping shopping = ticket.getShoppingList().get(i);
            assertEquals(shopping.getAmount(), shoppingObtained.getAmount());
            assertEquals(shopping.getDiscount(), shoppingObtained.getDiscount());
            assertEquals(shopping.getId(), shoppingObtained.getId());
            assertEquals(shopping.getRetailPrice(), shoppingObtained.getRetailPrice());
            assertEquals(shopping.getProduct().getCode(), shoppingObtained.getProduct().getCode());
            assertEquals(shopping.getDescription(), shoppingObtained.getDescription());
            assertEquals(shopping.getShoppingState(), shoppingObtained.getShoppingState());
        }
    }

    @Test
    public void testGetTicketTracking() {
        List<Ticket> ticketList = ticketDao.findAll();
        Ticket ticket = ticketList.get(new Random().nextInt(ticketList.size()));

        List<ShoppingTrackingWrapper> shoppingTrackingWrapperList = ticketController.getTicketTracking(ticket.getReference());

        assertNotNull(shoppingTrackingWrapperList);
        assertFalse(shoppingTrackingWrapperList.isEmpty());
        for (int i = 0; i < shoppingTrackingWrapperList.size(); i++) {
            ShoppingTrackingWrapper shoppingTrackingWrapper = shoppingTrackingWrapperList.get(i);
            Shopping shopping = ticket.getShoppingList().get(i);
            assertEquals(shopping.getProduct().getCode(), shoppingTrackingWrapper.getProductCode());
            assertEquals(shopping.getDescription(), shoppingTrackingWrapper.getDescription());
            assertEquals(shopping.getShoppingState(), shoppingTrackingWrapper.getShoppingState());
        }
    }

}

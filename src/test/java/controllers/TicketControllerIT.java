package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import wrappers.TicketIdWrapper;

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
    
    @Test
    public void testFindOneTicket(){
        assertNotNull(ticketController.findOneTicket(new TicketIdWrapper(1L)));
    }
    
    @Test
    public void testTicketIsClosedWithATicketWithAllShoppingsClosed(){
        Ticket ticketWithAllShoppingsClosed = new Ticket();
        Shopping shoppingClosed = new Shopping();
        shoppingClosed.setShoppingState(ShoppingState.CLOSED);
        ticketWithAllShoppingsClosed.addShopping(shoppingClosed);
        ticketWithAllShoppingsClosed.addShopping(shoppingClosed);
        assertTrue(ticketController.ticketIsClosed(ticketWithAllShoppingsClosed));
     }
    
    @Test
    public void testTicketIsClosedWithATicketWithAtLeastOneShoppingNotClosed(){
        Ticket ticketWithAtLeastOneShoppingNotClosed = new Ticket();
        Shopping shoppingOpened = new Shopping();
        shoppingOpened.setShoppingState(ShoppingState.OPENED);
        Shopping shoppingClosed = new Shopping();
        shoppingClosed.setShoppingState(ShoppingState.CLOSED);
        ticketWithAtLeastOneShoppingNotClosed.addShopping(shoppingClosed);
        ticketWithAtLeastOneShoppingNotClosed.addShopping(shoppingOpened);
        assertFalse(ticketController.ticketIsClosed(ticketWithAtLeastOneShoppingNotClosed));
    }
    
    @Test
    public void testTicketIsAssignedToInvoice(){
        Ticket ticketAssignedToAnInvoice = ticketDao.findOne(3L);     
        assertTrue(ticketController.ticketIsAssignedToInvoice(ticketAssignedToAnInvoice));
    }
    
    @Test
    public void testTicketIsNotAssignedToInvoice(){
        Ticket ticketNotAssignedToAnInvoice = ticketDao.findOne(1L);     
        assertFalse(ticketController.ticketIsAssignedToInvoice(ticketNotAssignedToAnInvoice));
    }

}

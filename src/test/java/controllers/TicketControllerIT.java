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
import daos.core.ArticleDao;
import daos.core.TicketDao;
import entities.core.Article;
import entities.core.Shopping;
import entities.core.ShoppingState;
import entities.core.Ticket;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.ShoppingUpdateWrapper;
import wrappers.TicketCreationWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class TicketControllerIT {

    @Autowired
    private TicketController ticketController;

    @Autowired
    private TicketDao ticketDao;
    
    @Autowired
    private ArticleDao articleDao;

    @Test
    public void testCreateTicketWithoutUser() {
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("article0");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        long lastTicketId = ticketDao.findFirstByOrderByCreatedDescIdDesc().getId();

        Ticket ticket = ticketController.createTicket(ticketCreationWrapper);
        List<Shopping> shoppingList = ticket.getShoppingList();
        Shopping shopping = shoppingList.get(0);
        Article article = articleDao.findFirstByCode(shoppingCreationWrapper.getProductCode());

        assertEquals(lastTicketId + 1, ticket.getId());
        assertNull(ticket.getUser());
        assertNotNull(shoppingList);
        assertEquals(shoppingCreationWrapper.getProductCode(), shopping.getProduct().getCode());
        assertEquals(shoppingCreationWrapper.getAmount(), shopping.getAmount());
        assertEquals(shoppingCreationWrapper.getDiscount(), shopping.getDiscount());
        assertEquals(ShoppingState.COMMITTED, shopping.getShoppingState());

        ticketDao.delete(ticket);
        article.setStock(article.getStock() + shoppingCreationWrapper.getAmount());
        articleDao.save(article);
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
    public void testUpdateTicket() {
        String productCode = "textilePrinting0";
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);
        Ticket createdTicket = ticketController.createTicket(ticketCreationWrapper);
        Shopping createdShopping = createdTicket.getShoppingList().get(0);
        
        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);
        Ticket updatedTicket = ticketController.updateTicket(createdTicket.getReference(), shoppingUpdateWrapperList);
        Shopping updatedShopping = updatedTicket.getShoppingList().get(0);

        assertEquals(createdTicket.getId(), updatedTicket.getId());
        assertEquals(createdTicket.getReference(), updatedTicket.getReference());
        assertEquals(createdTicket.getCreated(), updatedTicket.getCreated());
        assertNotNull(updatedShopping);
        assertEquals(createdShopping.getId(), updatedShopping.getId());
        assertEquals(createdShopping.getProduct(), updatedShopping.getProduct());
        assertEquals(createdShopping.getDescription(), updatedShopping.getDescription());
        assertEquals(createdShopping.getDiscount(), updatedShopping.getDiscount());
        assertEquals(createdShopping.getRetailPrice(), updatedShopping.getRetailPrice());
        assertFalse(createdShopping.getAmount() == updatedShopping.getAmount());
        assertFalse(createdShopping.getShoppingState() == updatedShopping.getShoppingState());
        
        assertEquals(shoppingUpdateWrapper.getAmount(), updatedShopping.getAmount());
        assertEquals(shoppingUpdateWrapper.getShoppingState(), updatedShopping.getShoppingState());

        ticketDao.delete(createdTicket);
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

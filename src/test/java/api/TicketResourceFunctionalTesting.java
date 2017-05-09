package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import entities.core.ShoppingState;
import entities.core.Ticket;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.ShoppingUpdateWrapper;
import wrappers.ShoppingWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketReferenceWrapper;
import wrappers.TicketWrapper;

public class TicketResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpOnce() {
        new RestService().seedDatabase();
    }

    @Test
    public void testCreateTicketNonexistentUserId() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();

        long userMobile = 9999999L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("article0");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<Ticket>(RestService.URL).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(token, "").clazz(Ticket.class)
                .post().build();
    }

    @Test
    public void testCreateTicketEmptyShoppingList() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        String token = new RestService().loginAdmin();

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        new RestBuilder<Ticket>(RestService.URL).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(token, "").clazz(Ticket.class)
                .post().build();
    }

    @Test
    public void testCreateTicketNotEnoughStock() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String token = new RestService().loginAdmin();

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("article2");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(token, "")
                .clazz(TicketReferenceWrapper.class).post().build();
    }

    @Test
    public void testCreateTicketInvalidProductStock() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        String token = new RestService().loginAdmin();

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("embroidery2");
        shoppingCreationWrapper.setAmount(0);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(token, "")
                .clazz(TicketReferenceWrapper.class).post().build();
    }
    
    @Test
    public void testCreateTicketInvalidProductDiscount() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        String token = new RestService().loginAdmin();

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("embroidery2");
        shoppingCreationWrapper.setAmount(10);
        shoppingCreationWrapper.setDiscount(120);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(token, "")
                .clazz(TicketReferenceWrapper.class).post().build();
    }

    @Test
    public void testCreateTicketWithUser() {
        String token = new RestService().loginAdmin();

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("article1");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        assertNotNull(ticketReference);
        assertFalse(ticketReference.getTicketReference().isEmpty());
    }

    @Test
    public void testCreateTicketWithoutUser() {
        String token = new RestService().loginAdmin();

        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("article0");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        assertNotNull(ticketReference);
        assertFalse(ticketReference.getTicketReference().isEmpty());
    }

    @Test
    public void testUpdateTicketNonexistentReference() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String ticketReference = "justTesting-123";
        String token = new RestService().loginAdmin();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper("article0", 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);
        new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS).pathId(ticketReference).body(shoppingUpdateWrapperList)
                .basicAuth(token, "").clazz(TicketReferenceWrapper.class).put().build();
    }

    @Test
    public void testUpdateTicket() {
        String productCode = "textilePrinting0";
        String token = new RestService().loginAdmin();
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);
        TicketReferenceWrapper ticketUpdatedReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .pathId(ticketReference.getTicketReference()).body(shoppingUpdateWrapperList).basicAuth(token, "")
                .clazz(TicketReferenceWrapper.class).put().build();

        assertEquals(ticketReference.getTicketReference(), ticketUpdatedReference.getTicketReference());
    }

    @Test
    public void testUpdateTicketNonexistentProductCodeInTicket() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("textilePrinting0");
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper("textilePrinting1", 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);
        new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS).pathId(ticketReference.getTicketReference())
                .body(shoppingUpdateWrapperList).basicAuth(token, "").clazz(TicketReferenceWrapper.class).put().build();
    }

    @Test
    public void testUpdateTicketInvalidProductAmount() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        String productCode = "textilePrinting0";
        String token = new RestService().loginAdmin();
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, -2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);
        new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS).pathId(ticketReference.getTicketReference())
                .body(shoppingUpdateWrapperList).basicAuth(token, "").clazz(TicketReferenceWrapper.class).put().build();
    }

    @Test
    public void testUpdateTicketNotEnoughProductStock() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String productCode = "article4";
        String token = new RestService().loginAdmin();
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, 50, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);
        new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS).pathId(ticketReference.getTicketReference())
                .body(shoppingUpdateWrapperList).basicAuth(token, "").clazz(TicketReferenceWrapper.class).put().build();
    }

    @Test
    public void testGetTicketTrackingNonexistentReference() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String ticketReference = "justTesting-123";
        String token = new RestService().loginAdmin();
        new RestBuilder<ShoppingTrackingWrapper[]>(RestService.URL).path(Uris.TICKETS).path(Uris.TRACKING).pathId(ticketReference)
                .basicAuth(token, "").clazz(ShoppingTrackingWrapper[].class).get().build();
    }

    @Test
    public void testGetTicket() {
        String token = new RestService().loginAdmin();

        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("embroidery0");
        shoppingCreationWrapper.setAmount(6);
        shoppingCreationWrapper.setDiscount(10);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        TicketWrapper ticketWrapper = new RestBuilder<TicketWrapper>(RestService.URL).path(Uris.TICKETS)
                .pathId(ticketReference.getTicketReference()).basicAuth(token, "").clazz(TicketWrapper.class).get().build();

        assertNotNull(ticketWrapper);
        assertEquals(ticketReference.getTicketReference(), ticketWrapper.getReference());
        assertNull(ticketWrapper.getUserMobile());

        ShoppingWrapper shoppingWrapper = ticketWrapper.getShoppingList().get(0);

        assertEquals(shoppingCreationWrapper.getProductCode(), shoppingWrapper.getProductCode());
        assertEquals(ShoppingState.OPENED, shoppingWrapper.getShoppingState());
    }

    @Test
    public void testGetTicketTracking() {
        String token = new RestService().loginAdmin();

        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("article0");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(RestService.URL).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(token, "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingTrackingWrapper> shoppingTrackingWrapperList = Arrays
                .asList(new RestBuilder<ShoppingTrackingWrapper[]>(RestService.URL).path(Uris.TICKETS).path(Uris.TRACKING)
                        .pathId(ticketReference.getTicketReference()).basicAuth(token, "").clazz(ShoppingTrackingWrapper[].class).get()
                        .build());

        assertNotNull(shoppingTrackingWrapperList);
        assertFalse(shoppingTrackingWrapperList.isEmpty());

        ShoppingTrackingWrapper shoppingTrackingWrapper = shoppingTrackingWrapperList.get(0);

        assertEquals(shoppingCreationWrapper.getProductCode(), shoppingTrackingWrapper.getProductCode());
        assertEquals(ShoppingState.COMMITTED, shoppingTrackingWrapper.getShoppingState());
    }

    @AfterClass
    public static void tearDownOnce() {
        new RestService().deleteAll();
    }

}

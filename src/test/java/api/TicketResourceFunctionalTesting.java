package api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import entities.core.Ticket;
import wrappers.ShoppingCreationWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketReferenceWrapper;

public class TicketResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpOnce() {
        new RestService().seedDatabase();
    }

    @Test
    public void testNonexistentUserId() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();

        long userMobile = 9999999L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductId(84000001111L);
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<Ticket>(RestService.URL).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(token, "").clazz(Ticket.class)
                .post().build();
    }

    @Test
    public void testEmptyShoppingList() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        String token = new RestService().loginAdmin();

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        new RestBuilder<Ticket>(RestService.URL).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(token, "").clazz(Ticket.class)
                .post().build();
    }

    @Test
    public void testCreateTicket() {
        String token = new RestService().loginAdmin();

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductId(84000001111L);
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

    @AfterClass
    public static void tearDownOnce() {
        new RestService().deleteAll();
    }

}

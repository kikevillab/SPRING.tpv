package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import api.wrappers.TicketReferenceCreatedPageWrapper;
import config.TestsApiConfig;
import entities.core.ShoppingState;
import entities.core.Ticket;
import wrappers.CashierClosuresCreationWrapper;
import wrappers.DayTicketWrapper;
import wrappers.ShoppingCreationWrapper;
import wrappers.ShoppingTrackingWrapper;
import wrappers.ShoppingUpdateWrapper;
import wrappers.ShoppingWrapper;
import wrappers.TicketCreationWrapper;
import wrappers.TicketReferenceWrapper;
import wrappers.TicketUpdateWrapper;
import wrappers.TicketUserPatchBodyWrapper;
import wrappers.TicketWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class TicketResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private void openCashier() {
        try {
            new RestBuilder<CashierClosuresCreationWrapper>(restService.getUrl()).path(Uris.CASHIER_CLOSURES)
                    .basicAuth(restService.loginAdmin(), "").clazz(CashierClosuresCreationWrapper.class).post().build();
        } catch (HttpClientErrorException http) {
        }
    }

    @Test
    public void testCreateTicketNonexistentUserId() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));

        long userMobile = 9999999L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("84000001111");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<Ticket>(restService.getUrl()).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "")
                .clazz(Ticket.class).post().build();
    }

    @Test
    public void testCreateTicketEmptyShoppingList() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        new RestBuilder<Ticket>(restService.getUrl()).path(Uris.TICKETS).body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "")
                .clazz(Ticket.class).post().build();
    }

    @Test
    public void testCreateTicketNotEnoughStock() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000001113");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).body(ticketCreationWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();
    }

    @Test
    public void testCreateTicketInvalidProductStock() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000002224");
        shoppingCreationWrapper.setAmount(0);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).body(ticketCreationWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();
    }

    @Test
    public void testCreateTicketInvalidProductDiscount() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));

        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000002224");
        shoppingCreationWrapper.setAmount(10);
        shoppingCreationWrapper.setDiscount(120);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).body(ticketCreationWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();
    }

    @Test
    public void testCreateTicketWithUser() {
        this.openCashier();
        long userMobile = 666000000L;
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        ticketCreationWrapper.setUserMobile(userMobile);

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000001111");
        shoppingCreationWrapper.setAmount(1);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        assertNotNull(ticketReference);
        assertFalse(ticketReference.getTicketReference().isEmpty());
    }

    @Test
    public void testCreateTicketWithoutUser() {
        this.openCashier();
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000001111");
        shoppingCreationWrapper.setAmount(1);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        assertNotNull(ticketReference);
        assertFalse(ticketReference.getTicketReference().isEmpty());
    }

    @Test
    public void testCreateTicketNonexistentVoucher() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));

        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000001111");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        ticketCreationWrapper.setVouchers(Arrays.asList(new String[] {"asdas"}));

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).body(ticketCreationWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();
    }

    @Test
    public void testUpdateTicketNonexistentReference() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String ticketReference = "notReference";

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper("84000001111", 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);

        TicketUpdateWrapper ticketUpdateWrapper = new TicketUpdateWrapper();
        ticketUpdateWrapper.setShoppingUpdateList(shoppingUpdateWrapperList);

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).pathId(ticketReference).body(ticketUpdateWrapper)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).patch().build();
    }

    @Test
    public void testUpdateTicket() {
        this.openCashier();
        String productCode = "8400000003333";
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);

        TicketUpdateWrapper ticketUpdateWrapper = new TicketUpdateWrapper();
        ticketUpdateWrapper.setShoppingUpdateList(shoppingUpdateWrapperList);

        TicketReferenceWrapper ticketUpdatedReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .pathId(ticketReference.getTicketReference()).body(ticketUpdateWrapper).basicAuth(restService.loginAdmin(), "")
                .clazz(TicketReferenceWrapper.class).patch().build();

        assertEquals(ticketReference.getTicketReference(), ticketUpdatedReference.getTicketReference());
    }

    @Test
    public void testUpdateTicketNonexistentProductCodeInTicket() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("84000003333");
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper("textilePrinting1", 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);

        TicketUpdateWrapper ticketUpdateWrapper = new TicketUpdateWrapper();
        ticketUpdateWrapper.setShoppingUpdateList(shoppingUpdateWrapperList);

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).pathId(ticketReference.getTicketReference())
                .body(ticketUpdateWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).patch().build();
    }

    @Test
    public void testUpdateTicketInvalidProductAmount() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        String productCode = "8400000003333";
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, -2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);

        TicketUpdateWrapper ticketUpdateWrapper = new TicketUpdateWrapper();
        ticketUpdateWrapper.setShoppingUpdateList(shoppingUpdateWrapperList);

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).pathId(ticketReference.getTicketReference())
                .body(ticketUpdateWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).patch().build();
    }

    @Test
    public void testUpdateTicketNotEnoughProductStock() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String productCode = "8400000001115";
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, 50, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);

        TicketUpdateWrapper ticketUpdateWrapper = new TicketUpdateWrapper();
        ticketUpdateWrapper.setShoppingUpdateList(shoppingUpdateWrapperList);

        new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS).pathId(ticketReference.getTicketReference())
                .body(ticketUpdateWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).patch().build();
    }

    @Test
    public void testUpdateTicketNonexistentVoucher() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String productCode = "8400000003333";
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();
        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode(productCode);
        shoppingCreationWrapper.setAmount(3);
        shoppingCreationWrapper.setDiscount(15);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingUpdateWrapper> shoppingUpdateWrapperList = new ArrayList<>();
        ShoppingUpdateWrapper shoppingUpdateWrapper = new ShoppingUpdateWrapper(productCode, 2, ShoppingState.COMMITTED);
        shoppingUpdateWrapperList.add(shoppingUpdateWrapper);

        TicketUpdateWrapper ticketUpdateWrapper = new TicketUpdateWrapper();
        ticketUpdateWrapper.setShoppingUpdateList(shoppingUpdateWrapperList);
        ticketUpdateWrapper.setVouchers(Arrays.asList(new String[] {"asdas"}));

        TicketReferenceWrapper ticketUpdatedReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .pathId(ticketReference.getTicketReference()).body(ticketUpdateWrapper).basicAuth(restService.loginAdmin(), "")
                .clazz(TicketReferenceWrapper.class).patch().build();

        assertEquals(ticketReference.getTicketReference(), ticketUpdatedReference.getTicketReference());
    }

    @Test
    public void testGetTicketTrackingNonexistentReference() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String ticketReference = "justTesting-123";
        new RestBuilder<ShoppingTrackingWrapper[]>(restService.getUrl()).path(Uris.TICKETS).path(Uris.TICKET_TRACKING).pathId(ticketReference)
                .basicAuth(restService.loginAdmin(), "").clazz(ShoppingTrackingWrapper[].class).get().build();
    }

    @Test
    public void testGetTicket() {
        this.openCashier();
        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000002222");
        shoppingCreationWrapper.setAmount(6);
        shoppingCreationWrapper.setDiscount(10);
        shoppingCreationWrapper.setDelivered(false);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        TicketWrapper ticketWrapper = new RestBuilder<TicketWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .pathId(ticketReference.getTicketReference()).basicAuth(restService.loginAdmin(), "").clazz(TicketWrapper.class).get()
                .build();

        assertNotNull(ticketWrapper);
        assertEquals(ticketReference.getTicketReference(), ticketWrapper.getReference());
        assertNull(ticketWrapper.getUserMobile());

        ShoppingWrapper shoppingWrapper = ticketWrapper.getShoppingList().get(0);

        assertEquals(shoppingCreationWrapper.getProductCode(), shoppingWrapper.getProductCode());
        assertEquals(ShoppingState.OPENED, shoppingWrapper.getShoppingState());
    }

    @Test
    public void testWholeDayTicketsMalformedDate() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        String date = "09-05-2017";
        new RestBuilder<DayTicketWrapper[]>(restService.getUrl()).path(Uris.TICKETS).path(Uris.DAY_TICKETS).pathId(date).basicAuth(restService.loginAdmin(), "")
                .clazz(DayTicketWrapper[].class).get().build();
    }

    //TODO Es complicado mantener la bd contolada sin borrar todo, este test es dificil
    public void testWholeDayTickets() {
        this.openCashier();
        int totalNumTickets = 6;
        double totalTicketsPrice = 1294.09;

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.US_DATE_FORMAT);
        Calendar today = Calendar.getInstance();
        String date = dateFormatter.format(today.getTime());
        List<DayTicketWrapper> wholeDayTickets = Arrays.asList(new RestBuilder<DayTicketWrapper[]>(restService.getUrl()).path(Uris.TICKETS)
                .path(Uris.DAY_TICKETS).pathId(date).basicAuth(restService.loginAdmin(), "").clazz(DayTicketWrapper[].class).get().build());

        double total = 0;
        for (DayTicketWrapper dayTicketWrapper : wholeDayTickets) {
            total += dayTicketWrapper.getTotal();
        }

        assertEquals(totalNumTickets, wholeDayTickets.size());
        assertEquals(totalTicketsPrice, total, 0.01);
    }

    @Test
    public void testGetTicketTracking() {
        this.openCashier();

        TicketCreationWrapper ticketCreationWrapper = new TicketCreationWrapper();

        List<ShoppingCreationWrapper> shoppingCreationWrapperList = new ArrayList<>();
        ShoppingCreationWrapper shoppingCreationWrapper = new ShoppingCreationWrapper();
        shoppingCreationWrapper.setProductCode("8400000001111");
        shoppingCreationWrapper.setAmount(2);
        shoppingCreationWrapper.setDiscount(0);
        shoppingCreationWrapper.setDelivered(true);
        shoppingCreationWrapperList.add(shoppingCreationWrapper);
        ticketCreationWrapper.setShoppingList(shoppingCreationWrapperList);

        TicketReferenceWrapper ticketReference = new RestBuilder<TicketReferenceWrapper>(restService.getUrl()).path(Uris.TICKETS)
                .body(ticketCreationWrapper).basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceWrapper.class).post().build();

        List<ShoppingTrackingWrapper> shoppingTrackingWrapperList = Arrays
                .asList(new RestBuilder<ShoppingTrackingWrapper[]>(restService.getUrl()).path(Uris.TICKETS).path(Uris.TICKET_TRACKING)
                        .pathId(ticketReference.getTicketReference()).basicAuth(restService.loginAdmin(), "")
                        .clazz(ShoppingTrackingWrapper[].class).get().build());

        assertNotNull(shoppingTrackingWrapperList);
        assertFalse(shoppingTrackingWrapperList.isEmpty());

        ShoppingTrackingWrapper shoppingTrackingWrapper = shoppingTrackingWrapperList.get(0);

        assertEquals(shoppingCreationWrapper.getProductCode(), shoppingTrackingWrapper.getProductCode());
        assertEquals(ShoppingState.COMMITTED, shoppingTrackingWrapper.getShoppingState());
    }

    @Test
    public void testGetTicketsByUserMobile() {
        this.openCashier();
        String userMobile = "666000002";
        String pageSize = "1";
        String pageNumber = "1";
 
        TicketReferenceCreatedPageWrapper ticketPage = new RestBuilder<TicketReferenceCreatedPageWrapper>(restService.getUrl())
                .path(Uris.TICKETS).param("mobile", userMobile).param("size", pageSize).param("page", pageNumber)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketReferenceCreatedPageWrapper.class).get().build();

        assertNotNull(ticketPage);
        assertEquals(Integer.valueOf(pageSize).intValue(), ticketPage.getNumberOfElements());
    }

    @Test
    public void testAssociateUserToTicketWithNonExistentTicket() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String nonExistentTicketReference = "nonExistentTicketReference";
        TicketUserPatchBodyWrapper bodyWrapper = new TicketUserPatchBodyWrapper();
        bodyWrapper.setUserMobile(666000002L);
        new RestBuilder<TicketWrapper>(restService.getUrl()).path(Uris.TICKETS).pathId(nonExistentTicketReference).path(Uris.TICKET_USER)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketWrapper.class).body(bodyWrapper).patch().build();
    }

    @Test
    public void testAssociateUserToTicketWithNonExistentUser() {
        this.openCashier();
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String nonExistentTicketReference = "nonExistentTicketReference";
        TicketUserPatchBodyWrapper bodyWrapper = new TicketUserPatchBodyWrapper();
        bodyWrapper.setUserMobile(666000002L);
        new RestBuilder<TicketWrapper>(restService.getUrl()).path(Uris.TICKETS).pathId(nonExistentTicketReference).path(Uris.TICKET_USER)
                .basicAuth(restService.loginAdmin(), "").clazz(TicketWrapper.class).body(bodyWrapper).patch().build();
    }
}

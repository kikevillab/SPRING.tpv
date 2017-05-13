package api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import entities.core.Invoice;
import wrappers.InvoiceWrapper;
import wrappers.TicketIdWrapper;

public class InvoiceResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpOnce() {
        new RestService().seedDatabase();
    }

    @Test
    public void testCreateInvoiceWithInvalidUser() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketIdWrapper ticketIdWrapper = new TicketIdWrapper(1L);
        String token = new RestService().loginAdmin();
        new RestBuilder<InvoiceWrapper>(RestService.URL).path(Uris.INVOICES).body(ticketIdWrapper).basicAuth(token, "").clazz(InvoiceWrapper.class).post()
                .build();
    }

    @Test
    public void testCreateInvoiceWithTicketAlreadyAssignedToAnInvoice() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketIdWrapper ticketIdWrapper = new TicketIdWrapper(3L);
        String token = new RestService().loginAdmin();
        new RestBuilder<InvoiceWrapper>(RestService.URL).path(Uris.INVOICES).body(ticketIdWrapper).basicAuth(token, "").clazz(InvoiceWrapper.class).post()
                .build();
    }
    
    @Test
    public void testCreateInvoiceWithTicketWithNotAllShoppingsClosed(){
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketIdWrapper ticketIdWrapper = new TicketIdWrapper(5L);
        String token = new RestService().loginAdmin();
        new RestBuilder<InvoiceWrapper>(RestService.URL).path(Uris.INVOICES).body(ticketIdWrapper).basicAuth(token, "").clazz(InvoiceWrapper.class).post()
                .build();
    }

    @Test
    public void testCreateInvoice() {
        TicketIdWrapper ticketIdWrapper = new TicketIdWrapper(6L);
        String token = new RestService().loginAdmin();
        InvoiceWrapper invoiceWrapper = new RestBuilder<InvoiceWrapper>(RestService.URL).path(Uris.INVOICES).body(ticketIdWrapper).basicAuth(token, "")
                .clazz(InvoiceWrapper.class).post().build();
        assertNotNull(invoiceWrapper);
    }

    @Test
    public void testFindAllInvoices() {
        String token = new RestService().loginAdmin();
        List<Invoice> invoices = Arrays.asList(new RestBuilder<Invoice[]>(RestService.URL).path(Uris.INVOICES).basicAuth(token, "")
                .clazz(Invoice[].class).get().build());
        assertNotNull(invoices);
        assertFalse(invoices.isEmpty());
    }

    @AfterClass
    public static void tearDownOnce() {
        new RestService().deleteAll();
    }
}
package api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;
import entities.core.Invoice;
import wrappers.InvoiceWrapper;
import wrappers.TicketReferenceWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class InvoiceResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateInvoiceWithInvalidUser() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketReferenceWrapper ticketReferenceWrapper = new TicketReferenceWrapper("ticket1");        
        new RestBuilder<Object>(restService.getUrl()).path(Uris.INVOICES).body(ticketReferenceWrapper).post()
                .build();
    }

    @Test
    public void testCreateInvoiceWithTicketAlreadyAssignedToAnInvoice() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        TicketReferenceWrapper ticketReferenceWrapper = new TicketReferenceWrapper("ticket3");   
        new RestBuilder<InvoiceWrapper>(restService.getUrl()).path(Uris.INVOICES).body(ticketReferenceWrapper).basicAuth(restService.loginAdmin(), "").post()
                .build();
    }

    @Test
    public void testCreateInvoice() {
        TicketReferenceWrapper ticketReferenceWrapper = new TicketReferenceWrapper("ticket6");   
        new RestBuilder<Object>(restService.getUrl()).path(Uris.INVOICES).body(ticketReferenceWrapper).basicAuth(restService.loginAdmin(), "")
                .post().build();
    }

    @Test
    public void testFindAllInvoices() {
        List<Invoice> invoices = Arrays.asList(new RestBuilder<Invoice[]>(restService.getUrl()).path(Uris.INVOICES).basicAuth(restService.loginAdmin(), "")
                .clazz(Invoice[].class).get().build());
        assertNotNull(invoices);
        assertFalse(invoices.isEmpty());
    }

}
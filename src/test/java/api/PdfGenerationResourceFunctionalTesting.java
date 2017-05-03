package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import wrappers.InvoiceIdWrapper;
import wrappers.TicketIdWrapper;

public class PdfGenerationResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        new RestService().seedDatabase();
    }

    @Test
    public void testGeneratePdfUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.INVOICES)
        .body(new InvoiceIdWrapper(20170001))
        .post()
        .build();      
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.TICKETS)
        .body(new TicketIdWrapper(1))
        .post()
        .build();
    }

    @Test
    public void testGenerateInvoicePdfWithManagerLoggedIn() {
        String token = new RestService().registerAndLoginManager();
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.INVOICES)
        .body(new InvoiceIdWrapper(20170001))
        .basicAuth(token, "")
        .post()
        .build();
    }
    
    @Test
    public void testGenerateTicketPdfWithManagerLoggedIn() {
        String token = new RestService().registerAndLoginManager();
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.TICKETS)
        .body(new TicketIdWrapper(1))
        .basicAuth(token, "")
        .post()
        .build();
    }
    
    @After
    public void tearDown(){
        new RestService().deleteAll();
    }
}

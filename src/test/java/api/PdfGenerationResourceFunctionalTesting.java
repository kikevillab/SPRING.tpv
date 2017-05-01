package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void testGenerateInvoicePdf() {
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.INVOICES)
        .body(new InvoiceIdWrapper(20170001))
        .post()
        .build();
    }
    
    @Test
    public void testGenerateTicketPdf() {
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.TICKETS)
        .body(new TicketIdWrapper(1))
        .post()
        .build();
    }
    
    @After
    public void tearDown(){
        new RestService().deleteAll();
    }
}

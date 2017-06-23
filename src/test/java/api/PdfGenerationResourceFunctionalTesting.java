package api;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import entities.core.Voucher;
import wrappers.InvoiceIdWrapper;
import wrappers.TicketIdWrapper;
import wrappers.VoucherIdWrapper;

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
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.VOUCHERS)
        .body(new VoucherIdWrapper(1))
        .post()
        .build();
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.BARCODES)
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
    
    @Test
    public void testGenerateVoucherPdfWithManagerLoggedIn() {
        String token = new RestService().registerAndLoginManager();
        Voucher voucher = Arrays
        .asList(new RestBuilder<Voucher[]>(RestService.URL).path(Uris.VOUCHERS).clazz(Voucher[].class).get().build()).get(0);
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.VOUCHERS)
        .body(new VoucherIdWrapper(voucher.getId()))
        .basicAuth(token, "")
        .post()
        .build();
    }
    
    @Test
    public void testGenerateBarcodesPdfWithManagerLoggedIn() {
        String[] productCodeList = {"8400000002222", "8400000002225", "8400000003334", "8400000003335"};
        String token = new RestService().registerAndLoginManager();
        new RestBuilder<Object>(RestService.URL).path(Uris.PDF_GENERATION + Uris.BARCODES)
        .body(Arrays.asList(productCodeList))
        .basicAuth(token, "")
        .post()
        .build();
    }
    
    @After
    public void tearDown(){
        new RestService().deleteAll();
    }
}

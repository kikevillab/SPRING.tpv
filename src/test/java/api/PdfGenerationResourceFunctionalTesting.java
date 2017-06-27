package api;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;
import entities.core.Voucher;
import wrappers.InvoiceIdWrapper;
import wrappers.TicketIdWrapper;
import wrappers.VoucherIdWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class PdfGenerationResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGeneratePdfUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.INVOICES).body(new InvoiceIdWrapper(20170001)).post()
                .build();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.TICKETS).body(new TicketIdWrapper(1)).post().build();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.VOUCHERS).body(new VoucherIdWrapper(1)).post()
                .build();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.BARCODES).post().build();
    }

    @Test
    public void testGenerateInvoicePdfWithManagerLoggedIn() {
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.INVOICES).basicAuth(restService.loginAdmin(), "")
                .body(new InvoiceIdWrapper(20171)).post().build();
    }

    @Test
    public void testGenerateTicketPdfWithManagerLoggedIn() {
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.TICKETS).body(new TicketIdWrapper(1))
                .basicAuth(restService.loginAdmin(), "").post().build();
    }

    @Test
    public void testGenerateVoucherPdfWithManagerLoggedIn() {
        Voucher voucher = Arrays
                .asList(new RestBuilder<Voucher[]>(restService.getUrl()).path(Uris.VOUCHERS).clazz(Voucher[].class).get().build()).get(0);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.VOUCHERS).body(new VoucherIdWrapper(voucher.getId()))
                .basicAuth(restService.loginAdmin(), "").post().build();
    }

    @Test
    public void testGenerateBarcodesPdfWithManagerLoggedIn() {
        String[] productCodeList = {"8400000002222", "8400000002225", "8400000003334", "8400000003335"};
        new RestBuilder<Object>(restService.getUrl()).path(Uris.PDF_GENERATION + Uris.BARCODES).body(Arrays.asList(productCodeList))
                .basicAuth(restService.loginAdmin(), "").post().build();
    }

}

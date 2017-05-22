package api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import entities.core.Voucher;
import wrappers.ActiveVouchersTotalValueWrapper;
import wrappers.VoucherCreationWrapper;

public class VoucherResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Voucher> vouchersToBeTested = new ArrayList<Voucher>();

    @BeforeClass
    public static void setUpOnce(){
        new RestService().seedDatabase();
    }
    
    @Before
    public void setUp() {
        
        vouchersToBeTested = Arrays
                .asList(new RestBuilder<Voucher[]>(RestService.URL).path(Uris.VOUCHERS).clazz(Voucher[].class).get().build());
        loggeamePapi("BEFORE");
    }

    private void loggeamePapi(String blabla){
        LogManager.getLogger(blabla+this.getClass()).info(vouchersToBeTested.get(0));
        LogManager.getLogger(blabla+this.getClass()).info(vouchersToBeTested.get(1));
        LogManager.getLogger(blabla+this.getClass()).info(vouchersToBeTested.get(2));
        LogManager.getLogger(blabla+this.getClass()).info(vouchersToBeTested.get(3));
        LogManager.getLogger(blabla+this.getClass()).info(vouchersToBeTested.get(4)+"\n");
    }
    
    @AfterClass
    public static void tearDown() {
        new RestService().deleteAll();
    }

    @Test
    public void testCreateVoucher() {
        String token = new RestService().loginAdmin();
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(new Random().nextDouble()));
        Calendar monthAfter = Calendar.getInstance();
        monthAfter.add(Calendar.MONTH, 1);
        voucherCreationWrapper.setExpiration(monthAfter);

        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).body(voucherCreationWrapper).basicAuth(token, "").post().build();
    }

    @Test
    public void testFindAllVouchers() {
        String token = new RestService().loginAdmin();
        List<Voucher> vouchers = Arrays.asList(
                new RestBuilder<Voucher[]>(RestService.URL).path(Uris.VOUCHERS).basicAuth(token, "").clazz(Voucher[].class).get().build());
        assertNotNull(vouchers);
    }

    @Test
    public void testConsumeVoucherWithNonExistentVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        loggeamePapi("AFTER");
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS + Uris.REFERENCE + Uris.VOUCHER_CONSUMPTION).pathId(0)
                .basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucherWithAlreadyConsumedVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String token = new RestService().loginAdmin();
        loggeamePapi("AFTER");
        String alreadyConsumedVoucherReference = vouchersToBeTested.get(3).getReference();
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS + Uris.REFERENCE + Uris.VOUCHER_CONSUMPTION)
                .pathId(alreadyConsumedVoucherReference).basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucherWithExpiredVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String token = new RestService().loginAdmin();
        loggeamePapi("AFTER");
        String expiredVoucherReference = vouchersToBeTested.get(4).getReference();
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS + Uris.REFERENCE + Uris.VOUCHER_CONSUMPTION)
                .pathId(expiredVoucherReference).basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucher() {
        String token = new RestService().loginAdmin();
        String reference = vouchersToBeTested.get(1).getReference();
        loggeamePapi("AFTER");
        new RestBuilder<Object>(RestService.URL)
        .path(Uris.VOUCHERS + Uris.REFERENCE + Uris.VOUCHER_CONSUMPTION).pathId(reference)
                .basicAuth(token, "").clazz(Object.class).put().build();
    }
    
    @Test
    public void testGetActiveVouchersTotalValue(){
        String token = new RestService().loginAdmin();
        ActiveVouchersTotalValueWrapper totalValue = new RestBuilder<ActiveVouchersTotalValueWrapper>(RestService.URL)
        .path(Uris.VOUCHERS + Uris.VOUCHER_ACTIVESTOTALVALUE)
        .basicAuth(token, "")
        .clazz(ActiveVouchersTotalValueWrapper.class)
        .get()
        .build();
        
        assertTrue(totalValue.getTotalValue().doubleValue() > 0.0);

    }
}

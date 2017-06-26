package api;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
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

    private List<Voucher> vouchersToBeTested = Arrays
            .asList(new RestBuilder<Voucher[]>(RestService.URL).path(Uris.VOUCHERS).clazz(Voucher[].class).get().build());

    @BeforeClass
    public static void setUpOnce() {
        new RestService().seedDatabase();
    }

    @AfterClass
    public static void tearDown() {
        new RestService().deleteAllExceptAdmin();
    }

    @Test
    public void testCreateVoucher() {
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(new Random().nextDouble()));
        Calendar monthAfter = Calendar.getInstance();
        monthAfter.add(Calendar.MONTH, 1);
        voucherCreationWrapper.setExpiration(monthAfter);
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).body(voucherCreationWrapper).post().build();
    }

    @Test
    public void testFindAllVouchers() {
        List<Voucher> vouchers = Arrays
                .asList(new RestBuilder<Voucher[]>(RestService.URL).path(Uris.VOUCHERS).clazz(Voucher[].class).get().build());
        assertTrue(vouchers.size() >= 5);
    }

    @Test
    public void testConsumeVoucherWithNonExistentVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).pathId(0).path(Uris.VOUCHER_CONSUMPTION).basicAuth(token, "")
                .clazz(Object.class).put().build();
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).pathId(0).path(Uris.VOUCHER_CONSUMPTION).basicAuth(token, "")
                .clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucherWithAlreadyConsumedVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String alreadyConsumedVoucherReference = vouchersToBeTested.get(3).getReference();
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).pathId(alreadyConsumedVoucherReference).path(Uris.VOUCHER_CONSUMPTION)
                .clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucherWithExpiredVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String token = new RestService().loginAdmin();
        String expiredVoucherReference = vouchersToBeTested.get(4).getReference();
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).pathId(expiredVoucherReference).path(Uris.VOUCHER_CONSUMPTION)
                .basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucher() {
        String reference = vouchersToBeTested.get(1).getReference();
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).pathId(reference).path(Uris.VOUCHER_CONSUMPTION).clazz(Object.class)
                .put().build();
    }

    @Test
    public void testGetActiveVouchersTotalValue() {
        ActiveVouchersTotalValueWrapper totalValue = new RestBuilder<ActiveVouchersTotalValueWrapper>(RestService.URL)
                .path(Uris.VOUCHERS + Uris.VOUCHER_ACTIVESTOTALVALUE).clazz(ActiveVouchersTotalValueWrapper.class).get().build();

        assertTrue(totalValue.getTotalValue().doubleValue() > 0.0);

    }
}

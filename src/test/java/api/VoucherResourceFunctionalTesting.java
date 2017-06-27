package api;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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
import wrappers.ActiveVouchersTotalValueWrapper;
import wrappers.VoucherCreationWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class VoucherResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Voucher> vouchersToBeTested() {
        return Arrays.asList(new RestBuilder<Voucher[]>(restService.getUrl()).path(Uris.VOUCHERS).clazz(Voucher[].class).get().build());
    }

    @Test
    public void testCreateVoucher() {
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(new Random().nextDouble()));
        Calendar monthAfter = Calendar.getInstance();
        monthAfter.add(Calendar.MONTH, 1);
        voucherCreationWrapper.setExpiration(monthAfter);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.VOUCHERS).body(voucherCreationWrapper).post().build();
    }

    @Test
    public void testFindAllVouchers() {
        List<Voucher> vouchers = Arrays
                .asList(new RestBuilder<Voucher[]>(restService.getUrl()).path(Uris.VOUCHERS).clazz(Voucher[].class).get().build());
        assertTrue(vouchers.size() >= 5);
    }

    @Test
    public void testConsumeVoucherWithNonExistentVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.VOUCHERS).pathId(0).path(Uris.VOUCHER_CONSUMPTION)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucherWithAlreadyConsumedVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String alreadyConsumedVoucherReference = this.vouchersToBeTested().get(3).getReference();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.VOUCHERS).pathId(alreadyConsumedVoucherReference)
                .path(Uris.VOUCHER_CONSUMPTION).basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucherWithExpiredVoucher() {
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        String expiredVoucherReference = this.vouchersToBeTested().get(4).getReference();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.VOUCHERS).pathId(expiredVoucherReference).path(Uris.VOUCHER_CONSUMPTION)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    @Test
    public void testConsumeVoucher() {
        String reference = this.vouchersToBeTested().get(1).getReference();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.VOUCHERS).pathId(reference).path(Uris.VOUCHER_CONSUMPTION)
                .basicAuth(restService.loginAdmin(), "").clazz(Object.class).put().build();
    }

    @Test
    public void testGetActiveVouchersTotalValue() {
        ActiveVouchersTotalValueWrapper totalValue = new RestBuilder<ActiveVouchersTotalValueWrapper>(restService.getUrl())
                .path(Uris.VOUCHERS + Uris.VOUCHER_ACTIVESTOTALVALUE).basicAuth(restService.loginAdmin(), "")
                .clazz(ActiveVouchersTotalValueWrapper.class).get().build();
        assertTrue(totalValue.getTotalValue().doubleValue() > 0.0);

    }
}

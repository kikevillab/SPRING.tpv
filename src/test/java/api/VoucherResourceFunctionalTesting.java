package api;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import entities.core.Voucher;
import wrappers.VoucherConsumptionWrapper;
import wrappers.VoucherCreationWrapper;

public class VoucherResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void consumeVoucherWithNonExistentVoucher(){
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        VoucherConsumptionWrapper voucher = new VoucherConsumptionWrapper();
        voucher.setId(0);
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).body(voucher).basicAuth(token, "").clazz(Object.class).put().build();
    }
}

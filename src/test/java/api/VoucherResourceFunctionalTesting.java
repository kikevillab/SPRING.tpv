package api;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import entities.core.Voucher;
import wrappers.VoucherCreationWrapper;

public class VoucherResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateVoucher() {
        String token = new RestService().loginAdmin();
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(25.6));
        new RestBuilder<Object>(RestService.URL).path(Uris.VOUCHERS).body(voucherCreationWrapper).basicAuth(token, "").post().build();
    }

    @Test
    public void testFindAllVouchers() {
        String token = new RestService().loginAdmin();
        List<Voucher> vouchers = Arrays.asList(
                new RestBuilder<Voucher[]>(RestService.URL).path(Uris.VOUCHERS).basicAuth(token, "").clazz(Voucher[].class).get().build());
        assertNotNull(vouchers);
    }
}

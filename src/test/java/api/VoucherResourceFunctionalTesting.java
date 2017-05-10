package api;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import wrappers.VoucherCreationWrapper;

public class VoucherResourceFunctionalTesting {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateVoucher() {
        String token = new RestService().loginAdmin();
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(25.6));
        new RestBuilder<Object>(RestService.URL)
        .path(Uris.VOUCHERS)
        .body(voucherCreationWrapper)
        .basicAuth(token, "")
        .post()
        .build();
    }
}

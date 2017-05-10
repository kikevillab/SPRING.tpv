package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controllers.VoucherController;
import wrappers.VoucherCreationWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.VOUCHERS)
public class VoucherResource {
    
    private VoucherController voucherController;
    
    @Autowired
    public void setVoucherController(VoucherController voucherController){
        this.voucherController = voucherController;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void createVoucher(@RequestBody VoucherCreationWrapper voucherCreationWrapper){
        voucherController.createVoucher(voucherCreationWrapper);
    }
}

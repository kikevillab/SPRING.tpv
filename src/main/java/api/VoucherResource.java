package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.VoucherAlreadyConsumedException;
import api.exceptions.VoucherNotFoundException;
import controllers.VoucherController;
import entities.core.Voucher;
import wrappers.VoucherConsumptionWrapper;
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
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Voucher> findAllVouchers(){
        return voucherController.findAllVouchers();
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void consumeVoucher(@RequestBody VoucherConsumptionWrapper voucherConsumptionWrapper) throws VoucherNotFoundException, VoucherAlreadyConsumedException{
        if(!voucherController.voucherExists(voucherConsumptionWrapper.getId())){
            throw new VoucherNotFoundException("Id: " + voucherConsumptionWrapper.getId());
        }
        if(voucherController.isVoucherConsumed(voucherConsumptionWrapper.getId())){
            throw new VoucherAlreadyConsumedException("Id: " + voucherConsumptionWrapper.getId());
        }
        voucherController.consumeVoucher(voucherConsumptionWrapper);
    }
}

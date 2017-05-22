package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.VoucherAlreadyConsumedException;
import api.exceptions.VoucherHasExpiredException;
import api.exceptions.VoucherNotFoundException;
import controllers.VoucherController;
import entities.core.Voucher;
import wrappers.ActiveVouchersTotalValueWrapper;
import wrappers.VoucherCreationWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.VOUCHERS)
public class VoucherResource {

    private VoucherController voucherController;

    @Autowired
    public void setVoucherController(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createVoucher(@RequestBody VoucherCreationWrapper voucherCreationWrapper) {
        voucherController.createVoucher(voucherCreationWrapper);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Voucher> findAllVouchers() {
        return voucherController.findAllVouchers();
    }
    
    @RequestMapping(value = Uris.REFERENCE, method = RequestMethod.GET)
    public Voucher findVoucherByReference(@PathVariable String reference) throws VoucherNotFoundException{
        throwExceptionIfVoucherDoesNotExist(reference);
        return voucherController.findVoucherByReference(reference);
    }
    
    @RequestMapping(value = Uris.VOUCHER_ACTIVESTOTALVALUE, method = RequestMethod.GET)
    public ActiveVouchersTotalValueWrapper getActiveVouchersTotalValue(){
        return voucherController.getActiveVouchersTotalValue();
    }

    @RequestMapping(value = Uris.REFERENCE + Uris.VOUCHER_CONSUMPTION, method = RequestMethod.PUT)
    public void consumeVoucher(@PathVariable String reference)
            throws VoucherNotFoundException, VoucherAlreadyConsumedException, VoucherHasExpiredException {
        throwExceptionIfVoucherDoesNotExist(reference);
        if (voucherController.voucherHasExpired(reference)) {
            throw new VoucherHasExpiredException("Reference: " + reference);
        }
        if (voucherController.isVoucherConsumed(reference)) {
            throw new VoucherAlreadyConsumedException("Reference: " + reference);
        }
        voucherController.consumeVoucher(reference);
    }
    
    private void throwExceptionIfVoucherDoesNotExist(String reference) throws VoucherNotFoundException{
        if (!voucherController.voucherExists(reference)) {
            throw new VoucherNotFoundException("Reference: " + reference);
        }
    }
}

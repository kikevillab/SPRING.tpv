package api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.VoucherAlreadyConsumedException;
import api.exceptions.VoucherHasExpiredException;
import api.exceptions.VoucherNotFoundException;
import config.ResourceNames;
import controllers.VoucherController;
import entities.core.Voucher;
import wrappers.ActiveVouchersTotalValueWrapper;
import wrappers.VoucherCreationResponseWrapper;
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
    public ResponseEntity<byte[]> createVoucher(@RequestBody VoucherCreationWrapper voucherCreationWrapper) throws IOException {
        VoucherCreationResponseWrapper responseWrapper = voucherController.createVoucher(voucherCreationWrapper);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = ResourceNames.VOUCHER_PDF_FILENAME_ROOT + responseWrapper.getVoucherId() + ResourceNames.PDF_FILE_EXT;
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(responseWrapper.getPdfByteArray(), headers,
                HttpStatus.OK);
        return response;
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

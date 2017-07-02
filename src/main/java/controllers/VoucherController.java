package controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.VoucherDao;
import entities.core.Voucher;
import services.PdfGenerationService;
import wrappers.ActiveVouchersTotalValueWrapper;
import wrappers.VoucherCreationResponseWrapper;
import wrappers.VoucherCreationWrapper;

@Controller
public class VoucherController {

    private VoucherDao voucherDao;

    private PdfGenerationService pdfGenService;

    @Autowired
    public void setVoucherDao(VoucherDao voucherDao) {
        this.voucherDao = voucherDao;
    }

    @Autowired
    public void setPdfGenerationService(PdfGenerationService pdfGenService) {
        this.pdfGenService = pdfGenService;
    }

    public VoucherCreationResponseWrapper createVoucher(VoucherCreationWrapper voucherCreationWrapper) throws IOException {
        Voucher voucher = new Voucher(voucherCreationWrapper.getValue(), voucherCreationWrapper.getExpiration());
        voucher = voucherDao.saveAndFlush(voucher);
        byte[] pdfByteArray = pdfGenService.generateVoucherPdf(voucher);
        return new VoucherCreationResponseWrapper(voucher.getId(), pdfByteArray);
    }

    public List<Voucher> findAllVouchers() {
        return voucherDao.findAll();
    }

    private List<Voucher> findAllActiveVouchers() {
        List<Voucher> activeVouchers = new ArrayList<>();
        for (Voucher voucher : findAllVouchers()) {
            if (!voucher.isExpired()) {
                activeVouchers.add(voucher);
            }
        }
        return activeVouchers;
    }

    public ActiveVouchersTotalValueWrapper getActiveVouchersTotalValue() {
        Double totalValue = 0.0;
        for (Voucher voucher : findAllActiveVouchers()) {
            totalValue += voucher.getValue().doubleValue();
        }
        return new ActiveVouchersTotalValueWrapper(new BigDecimal(totalValue));
    }

    public boolean voucherExists(String reference) {
        return voucherDao.findByReference(reference) != null;
    }

    public boolean isVoucherConsumed(String reference) {
        Voucher voucher = findVoucherByReference(reference);
        return voucher.isConsumed();
    }

    public void consumeVoucher(String reference) {
        Voucher voucher = findVoucherByReference(reference);
        voucher.consume();
        voucherDao.saveAndFlush(voucher);
    }

    public Voucher findVoucherByReference(String reference) {
        return voucherDao.findByReference(reference);
    }

    public boolean voucherHasExpired(String reference) {
        Voucher voucher = findVoucherByReference(reference);
        return voucher.isExpired();
    }
}

package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.VoucherDao;
import entities.core.Voucher;
import wrappers.ActiveVouchersTotalValueWrapper;
import wrappers.VoucherCreationWrapper;

@Controller
public class VoucherController {

    private VoucherDao voucherDao;
    
    @Autowired
    public void setVoucherDao(VoucherDao voucherDao){
        this.voucherDao = voucherDao;
    }
    
    public void createVoucher(VoucherCreationWrapper voucherCreationWrapper) {
        Voucher voucher = new Voucher(voucherCreationWrapper.getValue(), voucherCreationWrapper.getExpiration());
        voucherDao.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherDao.findAll();
    }
    
    private List<Voucher> findAllActiveVouchers() {
        List<Voucher> activeVouchers = new ArrayList<>();
        for(Voucher voucher : findAllVouchers()){
            if(!voucher.isExpired()){
                activeVouchers.add(voucher);
            }
        }
        return activeVouchers;
    }
    
    public ActiveVouchersTotalValueWrapper getActiveVouchersTotalValue(){
        double totalValue = 0.0;
        for(Voucher voucher : findAllActiveVouchers()){
            totalValue += voucher.getValue().doubleValue();
        }     
        return new ActiveVouchersTotalValueWrapper(new BigDecimal(totalValue));
    }

    public boolean voucherExists(String reference) {     
        return voucherDao.findByReference(reference) != null;
    }
    
    public boolean isVoucherConsumed(String reference){
        Voucher voucher = findVoucherByReference(reference);
        return voucher.isConsumed();
    }

    public void consumeVoucher(String reference) {
        Voucher voucher = findVoucherByReference(reference);
        voucher.consume();
        voucherDao.saveAndFlush(voucher);
    }

    public Voucher findVoucherByReference(String reference){
        return voucherDao.findByReference(reference);
    }
    public boolean voucherHasExpired(String reference) {
        Voucher voucher = findVoucherByReference(reference);
        return voucher.isExpired();
    }
}

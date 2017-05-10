package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.VoucherDao;
import entities.core.Voucher;
import wrappers.VoucherConsumptionWrapper;
import wrappers.VoucherCreationWrapper;

@Controller
public class VoucherController {

    private VoucherDao voucherDao;
    
    @Autowired
    public void setVoucherDao(VoucherDao voucherDao){
        this.voucherDao = voucherDao;
    }
    
    public void createVoucher(VoucherCreationWrapper voucherCreationWrapper) {
        Voucher voucher = new Voucher(voucherCreationWrapper.getValue());
        voucherDao.save(voucher);
    }

    public List<Voucher> findAllVouchers() {
        return voucherDao.findAll();
    }

    public boolean voucherExists(int id) {     
        return voucherDao.exists(id);
    }
    
    public boolean isVoucherConsumed(int id){
        Voucher voucher = voucherDao.findOne(id);
        return voucher.isConsumed();
    }

    public void consumeVoucher(VoucherConsumptionWrapper voucherConsumptionWrapper) {
        Voucher voucher = voucherDao.findOne(voucherConsumptionWrapper.getId());
        voucher.consume();
        voucherDao.save(voucher);
    }

    public boolean voucherExpired(int id) {
        Voucher voucher = voucherDao.findOne(id);
        boolean hasExpired = true; 
        return hasExpired;
    }

    
}

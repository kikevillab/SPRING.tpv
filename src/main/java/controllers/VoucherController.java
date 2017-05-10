package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.core.VoucherDao;
import entities.core.Voucher;
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

}

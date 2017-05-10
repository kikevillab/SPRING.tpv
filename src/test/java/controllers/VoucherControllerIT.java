package controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.core.VoucherDao;
import wrappers.VoucherCreationWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class VoucherControllerIT {

    @Autowired
    VoucherController voucherController;
    
    @Autowired
    VoucherDao voucherDao;
    
    @Test
    public void testCreateVoucher(){
        long previousCount = voucherDao.count();
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(25.6));
        voucherController.createVoucher(voucherCreationWrapper);
        assertEquals(previousCount + 1, voucherDao.count());
    }
}

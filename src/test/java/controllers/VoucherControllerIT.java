package controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

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
    private VoucherController voucherController;
    
    @Autowired
    private VoucherDao voucherDao;
         
    @Test
    public void testCreateVoucher(){
        long previousCount = voucherDao.count();
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(new Random().nextDouble()));
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.add(Calendar.DAY_OF_MONTH, 1);
        voucherCreationWrapper.setExpiration(dayAfter);
        voucherController.createVoucher(voucherCreationWrapper);    
        assertEquals(previousCount + 1, voucherDao.count());
        System.out.println(voucherDao.findAll());
        voucherDao.deleteAll();
    }
}

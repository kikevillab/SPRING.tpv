package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import wrappers.VoucherConsumptionWrapper;
import wrappers.VoucherCreationWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class VoucherControllerIT {

    @Autowired
    private VoucherController voucherController;

    @Autowired
    private VoucherDao voucherDao;

    @Test
    public void testVoucherHasExpired() {
        assertTrue(voucherController.voucherHasExpired(5));
        assertFalse(voucherController.voucherHasExpired(4));
    }

    @Test
    public void testCreateVoucher() {
        long previousCount = voucherDao.count();
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(new Random().nextDouble()));
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.add(Calendar.DAY_OF_MONTH, 1);
        voucherCreationWrapper.setExpiration(dayAfter);
        voucherController.createVoucher(voucherCreationWrapper);
        assertEquals(previousCount + 1, voucherDao.count());
        voucherDao.delete((int)voucherDao.count());
    }

    @Test
    public void testConsumeVoucher() {
        VoucherConsumptionWrapper voucherConsumptionWrapper = new VoucherConsumptionWrapper();
        voucherConsumptionWrapper.setId(1);
        voucherController.consumeVoucher(voucherConsumptionWrapper);
        assertTrue(voucherDao.findOne(1).isConsumed());
    }
    
    @Test
    public void testIsVoucherConsumed(){
        assertTrue(voucherController.isVoucherConsumed(4));
        assertFalse(voucherController.isVoucherConsumed(3));
    }
    
    @Test
    public void testVoucherExists(){
        assertTrue(voucherController.voucherExists(4));
        assertFalse(voucherController.voucherExists(0));
    }
}

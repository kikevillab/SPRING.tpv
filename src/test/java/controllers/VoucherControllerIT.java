package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
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
import entities.core.Voucher;
import wrappers.ActiveVouchersTotalValueWrapper;
import wrappers.VoucherCreationWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class VoucherControllerIT {

    @Autowired
    private VoucherController voucherController;

    @Autowired
    private VoucherDao voucherDao;

    @Test
    public void testCreateVoucher() throws IOException {
        long previousCount = voucherDao.count();
        VoucherCreationWrapper voucherCreationWrapper = new VoucherCreationWrapper();
        voucherCreationWrapper.setValue(new BigDecimal(new Random().nextDouble()));
        Calendar dayAfter = Calendar.getInstance();
        dayAfter.add(Calendar.DAY_OF_MONTH, 1);
        voucherCreationWrapper.setExpiration(dayAfter);
        voucherController.createVoucher(voucherCreationWrapper);
        assertEquals(previousCount + 1, voucherDao.count());
        voucherDao.delete((int) voucherDao.count());
    }

    @Test
    public void testGetActiveVouchersTotalValue() {
        ActiveVouchersTotalValueWrapper totalValueWrapper = voucherController.getActiveVouchersTotalValue();
        assertTrue(totalValueWrapper.getTotalValue().doubleValue() >= 0.0);
    }

    @Test
    public void testVoucherExists() {
        Voucher existentVoucher = voucherDao.findOne(4);
        assertTrue(voucherController.voucherExists(existentVoucher.getReference()));
        assertFalse(voucherController.voucherExists("---------"));
    }

    @Test
    public void testIsVoucherConsumed() {
        Voucher consumedVoucher = voucherDao.findOne(4);
        Voucher noConsumedVoucher = voucherDao.findOne(3);
        assertTrue(voucherController.isVoucherConsumed(consumedVoucher.getReference()));
        assertFalse(voucherController.isVoucherConsumed(noConsumedVoucher.getReference()));
    }

    @Test
    public void testConsumeVoucher() {
        Voucher voucher = voucherDao.findOne(1);
        voucherController.consumeVoucher(voucher.getReference());
        assertTrue(voucherDao.findOne(1).isConsumed());
    }

    @Test
    public void testFindVoucherByReference(){
        Voucher voucher = voucherDao.findOne(1);
        assertNotNull(voucherController.findVoucherByReference(voucher.getReference()));
        assertNull(voucherController.findVoucherByReference("reference"));
    }
    
    @Test
    public void testVoucherHasExpired() {
        Voucher expiredVoucher = voucherDao.findOne(5);
        Voucher noExpiredVoucher = voucherDao.findOne(4);
        assertTrue(voucherController.voucherHasExpired(expiredVoucher.getReference()));
        assertFalse(voucherController.voucherHasExpired(noExpiredVoucher.getReference()));
    }

}

package controllers;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.core.CashierClosureDao;
import entities.core.CashierClosure;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class CashierClosureControllerIT extends TestCase {

    @Autowired
    private CashierClosuresController cashierClosuresController;

    @Autowired
    private CashierClosureDao cashierClosureDao;
    
   
    @After
    public void after()
    {
    	cashierClosureDao.deleteAll();
    }
    
    @Test
    public void shouldReturnLastCashierIsClosedWhenNoCashierClosuresExist()
    {
    	assertTrue(cashierClosuresController.isLastCashierClosuresClosed());
    }
    
    public void shouldReturnLastCashierIsNotClosed()
    {
    	cashierClosuresController.createCashierClosures();
    	
    	assertFalse(cashierClosuresController.isLastCashierClosuresClosed());
    }
    
    @Test
    public void shouldReturnCashierClosureWellformed()
    {
    	CashierClosure cashierClosure = cashierClosuresController.createCashierClosures();
    	
    	assertNotNull(cashierClosure);
    	assertEquals(0.0, cashierClosure.getAmount(), 0.01);
    	assertEquals(cashierClosure.getClosureDate(), null);
    	assertEquals(cashierClosuresController.getLastCashierClosure().getId(), cashierClosure.getId());
    	
    }
   
    
    @Test
    public void shouldIncreaseAmount()
    {
    	cashierClosuresController.createCashierClosures();

    	cashierClosuresController.depositCashierRequest(5);
    	
    	assertEquals(5.0, cashierClosureDao.findFirstByOrderByOpeningDateDesc().getAmount(), 0.01);
    }
    
    @Test
    public void shouldDecreaseAmount()
    {
    	cashierClosuresController.createCashierClosures();

    	cashierClosuresController.withDrawCashierRequest(3);
    	
    	assertEquals(-3.0, cashierClosureDao.findFirstByOrderByOpeningDateDesc().getAmount(), 0.01);

    }
    
    @Test
    public void shouldCloseCashier()
    {
    	cashierClosuresController.createCashierClosures();

    	CashierClosure closesCashier = cashierClosuresController.closeCashierRequest(10, "comentario");
    	
    	CashierClosure lastCashierClosure = cashierClosuresController.getLastCashierClosure();
    	
    	assertEquals(lastCashierClosure.getId(),closesCashier.getId());
    	assertEquals(10.0, lastCashierClosure.getAmount(), 0.01);
    	assertNotNull(lastCashierClosure.getClosureDate());
    	assertTrue(cashierClosuresController.isLastCashierClosuresClosed());
    }
    
}

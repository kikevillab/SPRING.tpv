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
import daos.core.CashierClosuresDao;
import entities.core.CashierClosures;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class CashierClosuresControllerIT extends TestCase {

    @Autowired
    private CashierClosuresController cashierClosuresController;

    @Autowired
    private CashierClosuresDao cashierClosuresDao;
    
   
    @After
    public void after()
    {
    	cashierClosuresDao.deleteAll();
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
    	CashierClosures cashierClosures = cashierClosuresController.createCashierClosures();
    	
    	assertNotNull(cashierClosures);
    	assertEquals(0, cashierClosures.getAmount());
    	assertEquals(cashierClosures.getClosureDate(), null);
    	assertEquals(cashierClosuresController.getLastCashierClosure().getId(), cashierClosures.getId());
    	
    }
   
    
    @Test
    public void shouldIncreaseAmount()
    {
    	cashierClosuresController.createCashierClosures();

    	cashierClosuresController.depositCashierRequest(5);
    	
    	assertEquals(5, cashierClosuresDao.findFirstByOrderByOpeningDateDesc().getAmount());
    }
    
    @Test
    public void shouldDecreaseAmount()
    {
    	cashierClosuresController.createCashierClosures();

    	cashierClosuresController.withDrawCashierRequest(3);
    	
    	assertEquals(-3, cashierClosuresDao.findFirstByOrderByOpeningDateDesc().getAmount());

    }
    
    @Test
    public void shouldCloseCashier()
    {
    	cashierClosuresController.createCashierClosures();

    	CashierClosures closesCashier = cashierClosuresController.closeCashierRequest(10, "comentario");
    	
    	CashierClosures lastCashierClosure = cashierClosuresController.getLastCashierClosure();
    	
    	assertEquals(lastCashierClosure.getId(),closesCashier.getId());
    	assertEquals(10, lastCashierClosure.getAmount());
    	assertNotNull(lastCashierClosure.getClosureDate());
    	assertTrue(cashierClosuresController.isLastCashierClosuresClosed());
    }
    
}

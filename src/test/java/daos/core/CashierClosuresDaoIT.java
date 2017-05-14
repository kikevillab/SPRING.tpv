package daos.core;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import entities.core.Article;
import entities.core.CashierClosures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertNotEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class CashierClosuresDaoIT {
    @Autowired
    private CashierClosuresDao cashierClosuresDao;


    @Test
    public void testCashierClosuresPersistence() {
        CashierClosures cashierClosures = new CashierClosures(0, 5, new GregorianCalendar());
        cashierClosures = cashierClosuresDao.saveAndFlush(cashierClosures);

        assertNotEquals(0, cashierClosures.getId());
    }
}

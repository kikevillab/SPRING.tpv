package daos.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import daos.users.TokenDao;
import daos.users.UserDao;
import entities.users.Token;
import entities.users.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TokenDaoIT {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;
    
    @Autowired
    private Environment environment;

    @Test
    public void testFindByUser() {
        User user = userDao.findByMobile(666000000);
        assertNotNull(tokenDao.findByUser(user));
        assertNull(tokenDao.findByUser(userDao.findByMobile(666000001)));
    }
    
    @Test
    public void testDeleteByCreationDateLessThan() {
        List<Token> t = tokenDao.findAll();
        assertEquals(0,tokenDao.deleteByCreationDateLessThan(new Date(new Date().getTime() - Integer.parseInt(environment.getProperty("tokenTime.user")))));
        assertEquals(1, tokenDao.count());
        assertEquals(1,tokenDao.deleteByCreationDateLessThan(new Date(new Date().getTime() - Integer.parseInt(environment.getProperty("tokenTime.user")) + Integer.parseInt(environment.getProperty("tokenTime.user")) + 1000 )));
        assertEquals(0, tokenDao.count());
        tokenDao.save(t);
    }

}

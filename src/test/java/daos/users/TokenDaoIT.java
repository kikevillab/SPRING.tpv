package daos.users;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import daos.users.TokenDao;
import daos.users.UserDao;
import entities.users.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TokenDaoIT {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Test
    public void testFindByUser() {
        User user = userDao.findByMobile(666000000);
        assertNotNull(tokenDao.findByUser(user));
        assertNull(tokenDao.findByUser(userDao.findByMobile(666000001)));
    }

}

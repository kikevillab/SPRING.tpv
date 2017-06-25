package daos.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import entities.users.Role;
import entities.users.Token;
import entities.users.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class UserDaoIT {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Test
    public void testCreate() {
        assertTrue(userDao.count() >= 4);
    }

    @Test
    public void testFindByTokenValue() {
        User user = userDao.findByMobile(666000000);
        Token token = tokenDao.findByUser(user);
        assertEquals(user, userDao.findByTokenValue(token.getValue()));
        assertNull(userDao.findByTokenValue("kk"));
    }

    @Test
    public void testFindAllAndRole() {
        Pageable pageable = new PageRequest(0, 4);
        Page<User> userCustomer = userDao.findAllAndRole(pageable, Role.CUSTOMER);
        assertNotNull(userCustomer);
        assertTrue(userCustomer.getSize() > 0);
        Page<User> userOperator = userDao.findAllAndRole(pageable, Role.OPERATOR);
        assertNotNull(userOperator);
        assertTrue(userOperator.getSize() > 0);
        Page<User> userManager = userDao.findAllAndRole(pageable, Role.MANAGER);
        assertNotNull(userManager);
        assertTrue(userManager.getSize() > 0);
    }

    @Test
    public void testFindByMobileAndRole() {
        long mobileCustomer = 666000001;
        long mobileManager = 666000005;
        long mobileOperator = 666000004;

        User userCustomer = userDao.findByMobileAndRole(Role.CUSTOMER, mobileCustomer);
        assertNotNull(userCustomer);
        assertTrue(mobileCustomer == userCustomer.getMobile());

        User userOperator = userDao.findByMobileAndRole(Role.OPERATOR, mobileOperator);
        assertNotNull(userOperator);
        assertTrue(mobileOperator == userOperator.getMobile());

        User userManager = userDao.findByMobileAndRole(Role.MANAGER, mobileManager);
        assertNotNull(userManager);
        assertTrue(mobileManager == userManager.getMobile());
    }

    @Test
    public void testFindByDniAndRole() {
        String dniCustomer = "1235678X";
        String dniOperator = "1245538L";
        String dniManager = "1232578M";

        User userCustomer = userDao.findByDniAndRole(Role.CUSTOMER, dniCustomer);
        assertNotNull(userCustomer);
        assertTrue(dniCustomer.equals(userCustomer.getDni()));

        User userOperator = userDao.findByDniAndRole(Role.OPERATOR, dniOperator);
        assertNotNull(userOperator);
        assertTrue(dniOperator.equals(userOperator.getDni()));

        User userManager = userDao.findByDniAndRole(Role.MANAGER, dniManager);
        assertNotNull(userManager);
        assertTrue(dniManager.equals(userManager.getDni()));
    }

    @Test
    public void testFindByEmailAndRole() {
        String emailCustomer = "user2@user2.com";
        String emailOperator = "userO@userO.com";
        String emailManager = "userM@userM.com";

        User userCustomer = userDao.findByEmailAndRole(Role.CUSTOMER, emailCustomer);
        assertNotNull(userCustomer);
        assertTrue(emailCustomer.equals(userCustomer.getEmail()));

        User userOperator = userDao.findByEmailAndRole(Role.OPERATOR, emailOperator);
        assertNotNull(userOperator);
        assertTrue(emailOperator.equals(userOperator.getEmail()));

        User userManager = userDao.findByEmailAndRole(Role.MANAGER, emailManager);
        assertNotNull(userManager);
        assertTrue(emailManager.equals(userManager.getEmail()));
    }

    @Test
    public void testFindAllAndRoleExtrem() {
        Pageable pageable = new PageRequest(0, 4);
        Page<User> userCustomer = userDao.findAllAndRole(null, Role.CUSTOMER);
        assertNotNull(userCustomer);
        assertTrue(userCustomer.getSize() == 0);
        assertTrue(userCustomer.getContent().size() > 0);
        Page<User> userOperator = userDao.findAllAndRole(pageable, null);
        assertNotNull(userOperator);
        assertTrue(userOperator.getContent().size() == 0);
        assertTrue(userOperator.getSize() > 0);
    }

    @Test
    public void testFindByMobileAndRoleExtrem() {
        long mobileCustomer = -1;
        long mobileOperator = 666000004;

        User userCustomer = userDao.findByMobileAndRole(Role.CUSTOMER, mobileCustomer);
        assertNull(userCustomer);
        User userOperator = userDao.findByMobileAndRole(null, mobileOperator);
        assertNull(userOperator);
    }

    @Test
    public void testFindByDniAndRoleExtrem() {
        String dniCustomer = null;
        String dniOperator = "1245538L";
        String dniManager = "-1-1-1";

        User userCustomer = userDao.findByDniAndRole(Role.CUSTOMER, dniCustomer);
        assertNull(userCustomer);
        User userOperator = userDao.findByDniAndRole(null, dniOperator);
        assertNull(userOperator);
        User userManager = userDao.findByDniAndRole(Role.MANAGER, dniManager);
        assertNull(userManager);
    }

    @Test
    public void testFindByEmailAndRoleExtrem() {
        String emailCustomer = null;
        String emailOperator = "userO@userO.com";
        String emailManager = "-1";

        User userCustomer = userDao.findByEmailAndRole(Role.CUSTOMER, emailCustomer);
        assertNull(userCustomer);
        User userOperator = userDao.findByEmailAndRole(null, emailOperator);
        assertNull(userOperator);
        User userManager = userDao.findByEmailAndRole(Role.MANAGER, emailManager);
        assertNull(userManager);
    }

}

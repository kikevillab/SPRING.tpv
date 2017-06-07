package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import api.exceptions.NotFoundTicketReferenceException;
import api.exceptions.NotFoundUserMobileException;
import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.users.UserDao;
import entities.core.Ticket;
import entities.users.Role;
import entities.users.User;
import wrappers.UserDetailsWrapper;
import wrappers.UserWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class UserControllerIT {

    private static final long USER_MOBILE = 666666666;

    private static final String USER_NAME = "username";

    private static final String USER_PASSWORD = "password";
    
    private static final String USER_DNI = "1235648E";
    
    private static final String USER_ADRESS = "Calle Gran Vía, 26. Madrid";
    
    private static final String USER_EMAIL = "tpv@tpv.com";
    
    private static final boolean USER_ACTIVATE = true;

    private static final String INVALID_TICKET_REFERENCE = "wg_yXs1LJmSvNsld-aXBg27P1jA";
    
    @Autowired
    UserController userController;

    @Autowired
    UserDao userDao;
    
    @Autowired
    private TicketController ticketController;

    @Test
    public void testFindUserByMobilePhone() {
        UserDetailsWrapper user = userController.findUserByMobilePhone(666000000L);
        assertNotNull(user);
    }

    @Test
    public void testFindAllUsers() {
        List<UserDetailsWrapper> users = userController.findAllUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    public void testUpdateUser() {
        User user = userDao.findOne(1);
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setId(user.getId());
        userWrapper.setAddress("address");
        userWrapper.setDni(user.getDni());
        userWrapper.setEmail(user.getEmail());
        userWrapper.setMobile(user.getMobile());
        userWrapper.setUsername(user.getUsername());
        userWrapper.setPassword(user.getPassword());
        userWrapper.setActive(user.isActive());
        userController.updateUser(userWrapper);
        user = userDao.findOne(1);
        assertEquals("address", user.getAddress());
    }

    @Test
    public void testFindAllAndRole() {

        Pageable pageable = new PageRequest(0, 4);
        Page<UserWrapper> userCustomer = userController.getAllAndRole(pageable, Role.CUSTOMER);
        assertNotNull(userCustomer);
        assertTrue(userCustomer.getSize() > 0);
        Page<UserWrapper> userOperator = userController.getAllAndRole(pageable, Role.OPERATOR);
        assertNotNull(userOperator);
        assertTrue(userOperator.getSize() > 0);
        Page<UserWrapper> userManager = userController.getAllAndRole(pageable, Role.MANAGER);
        assertNotNull(userManager);
        assertTrue(userManager.getSize() > 0);
    }

    @Test
    public void testFindByMobileAndRole() {

        long mobileCustomer = 666000001;
        long mobileManager = 666000005;
        long mobileOperator = 666000004;

        UserWrapper userCustomer = userController.getByMobileAndRole(mobileCustomer, Role.CUSTOMER);
        assertNotNull(userCustomer);
        assertTrue(mobileCustomer == userCustomer.getMobile());

        UserWrapper userOperator = userController.getByMobileAndRole(mobileOperator, Role.OPERATOR);
        assertNotNull(userOperator);
        assertTrue(mobileOperator == userOperator.getMobile());

        UserWrapper userManager = userController.getByMobileAndRole(mobileManager, Role.MANAGER);
        assertNotNull(userManager);
        assertTrue(mobileManager == userManager.getMobile());
    }

    @Test
    public void testFindByDniAndRole() {

        String dniCustomer = "1235678X";
        String dniOperator = "1245538L";
        String dniManager = "1232578M";

        UserWrapper userCustomer = userController.getByDniAndRole(dniCustomer, Role.CUSTOMER);
        assertNotNull(userCustomer);
        assertTrue(dniCustomer.equals(userCustomer.getDni()));

        UserWrapper userOperator = userController.getByDniAndRole(dniOperator, Role.OPERATOR);
        assertNotNull(userOperator);
        assertTrue(dniOperator.equals(userOperator.getDni()));

        UserWrapper userManager = userController.getByDniAndRole(dniManager, Role.MANAGER);
        assertNotNull(userManager);
        assertTrue(dniManager.equals(userManager.getDni()));
    }

    @Test
    public void testFindByEmailAndRole() {

        String emailCustomer = "user2@user2.com";
        String emailOperator = "userO@userO.com";
        String emailManager = "userM@userM.com";

        UserWrapper userCustomer = userController.getByEmailAndRole(emailCustomer, Role.CUSTOMER);
        assertNotNull(userCustomer);
        assertTrue(emailCustomer.equals(userCustomer.getEmail()));

        UserWrapper userOperator = userController.getByEmailAndRole(emailOperator, Role.OPERATOR);
        assertNotNull(userOperator);
        assertTrue(emailOperator.equals(userOperator.getEmail()));

        UserWrapper userManager = userController.getByEmailAndRole(emailManager, Role.MANAGER);
        assertNotNull(userManager);
        assertTrue(emailManager.equals(userManager.getEmail()));
    }

    @Test
    public void testFindAllAndRoleExtrem() {

        Pageable pageable = null;
        Page<UserWrapper> userCustomer = userController.getAllAndRole(pageable, Role.CUSTOMER);
        assertNotNull(userCustomer);
        assertTrue(userCustomer.getSize() == 0);
        assertTrue(userCustomer.getContent().size() > 0);
        pageable = new PageRequest(0, 4);
        Page<UserWrapper> userOperator = userController.getAllAndRole(pageable, null);
        assertNotNull(userOperator);
        assertTrue(userOperator.getContent().size() == 0);
        assertTrue(userOperator.getSize() > 0);
    }

    @Test
    public void testFindByMobileAndRoleExtrem() {

        long mobileCustomer = -1;
        long mobileOperator = 666000004;

        UserWrapper userCustomer = userController.getByMobileAndRole(mobileCustomer, Role.CUSTOMER);
        assertNull(userCustomer);
        UserWrapper userOperator = userController.getByMobileAndRole(mobileOperator, null);
        assertNull(userOperator);

    }

    @Test
    public void testFindByDniAndRoleExtrem() {

        String dniCustomer = null;
        String dniOperator = "1245538L";
        String dniManager = "-1-1-1";

        UserWrapper userCustomer = userController.getByDniAndRole(dniCustomer, Role.CUSTOMER);
        assertNull(userCustomer);
        UserWrapper userOperator = userController.getByDniAndRole(dniOperator, null);
        assertNull(userOperator);
        UserWrapper userManager = userController.getByDniAndRole(dniManager, Role.MANAGER);
        assertNull(userManager);

    }

    @Test
    public void testFindByEmailAndRoleExtrem() {

        String emailCustomer = null;
        String emailOperator = "userO@userO.com";
        String emailManager = "-1";

        UserWrapper userCustomer = userController.getByEmailAndRole(emailCustomer, Role.CUSTOMER);
        assertNull(userCustomer);
        UserWrapper userOperator = userController.getByEmailAndRole(emailOperator, null);
        assertNull(userOperator);
        UserWrapper userManager = userController.getByEmailAndRole(emailManager, Role.MANAGER);
        assertNull(userManager);

    }
    
    
    @Test
    public void testRegistration() {
        UserWrapper user = new UserWrapper(USER_MOBILE, USER_NAME,USER_PASSWORD,USER_DNI,USER_ADRESS,USER_EMAIL,USER_ACTIVATE);
        assertTrue(userController.registration(user, Role.CUSTOMER));
        assertFalse(userController.registration(user, Role.CUSTOMER));

    }

    @Test
    public void testRegistrationExtrem() {
        UserWrapper user = new UserWrapper(USER_MOBILE, USER_NAME,USER_PASSWORD,USER_DNI,USER_ADRESS,USER_EMAIL,USER_ACTIVATE);
        assertFalse(userController.registration(null, Role.CUSTOMER));
        assertFalse(userController.registration(user,null));
    }
    
    @Test
    public void testDeleteUserException() {
        try {
            long invalidNumber=-1;
            this.userController.deleteUser(invalidNumber);
            fail();
        } catch (NotFoundUserMobileException exception) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetByTicketReference() {
        List<Ticket> tickets = this.ticketController.findAll();

        try {
            UserWrapper user = this.userController.getByTicketReference(tickets.get(1).getReference());
            assertEquals(tickets.get(1).getUser().getMobile(), user.getMobile());
        } catch (NotFoundTicketReferenceException exception) {
            LogManager.getLogger(this.getClass()).info("testGetByTicketReference was wrong:  " + exception.getMessage());
            fail();
        }
    }
    
    @Test
    public void testGetByTicketReferenceWithWrongTicketReference() {
        try {
            this.userController.getByTicketReference(INVALID_TICKET_REFERENCE);
            fail();
        } catch (NotFoundTicketReferenceException exception) {
            assertTrue(true);
        }
    }

    
    @Test
    public void testDeleteUser() {
        try {
            long mobile=666000099;
            userController.registration(new UserWrapper(mobile, "usuarioDelete", "passDelete"), Role.CUSTOMER);
            this.userController.deleteUser(mobile);
            assertFalse(this.userController.userMobileExists(mobile));
        } catch (NotFoundUserMobileException exception) {
            fail();
        }
    }
    
    

}

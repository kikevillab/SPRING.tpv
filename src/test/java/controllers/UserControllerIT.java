package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsControllerConfig;
import config.TestsPersistenceConfig;
import daos.users.UserDao;
import wrappers.UserDetailsWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class UserControllerIT {

    @Autowired
    UserController userController;
    
    @Autowired
    UserDao userDao;
    
    @Test
    public void testFindUserByMobilePhone(){
        UserDetailsWrapper user = userController.findUserByMobilePhone(666000000L);
        assertNotNull(user);
    }
    
    @Test
    public void testFindAllUsers(){
        List<UserDetailsWrapper> users = userController.findAllUsers();
        assertFalse(users.isEmpty());
    }
}

package controllers;

import static org.junit.Assert.assertEquals;
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
import entities.users.User;
import wrappers.UserDetailsWrapper;
import wrappers.UserUpdateWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class, TestsControllerConfig.class})
public class UserControllerIT {

    @Autowired
    UserController userController;

    @Autowired
    UserDao userDao;

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
        UserUpdateWrapper userWrapper = new UserUpdateWrapper();
        userWrapper.setId(user.getId());
        userWrapper.setAddress("address");
        userWrapper.setDni(user.getDni());
        userWrapper.setEmail(user.getEmail());
        userWrapper.setMobile(user.getMobile());
        userWrapper.setUsername(user.getUsername());
        userWrapper.setPassword(user.getPassword());
        userController.updateUser(userWrapper);
        user = userDao.findOne(1);
        assertEquals("address", user.getAddress());
    }
}

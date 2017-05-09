package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import daos.users.AuthorizationDao;
import daos.users.UserDao;
import entities.users.Authorization;
import entities.users.Role;
import entities.users.User;
import wrappers.UserDetailsWrapper;
import wrappers.UserUpdateWrapper;
import wrappers.UserWrapper;

@Controller
public class UserController {

    private UserDao userDao;

    private AuthorizationDao authorizationDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setAuthorizationDao(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    public boolean registration(UserWrapper userWrapper, Role role) {
        if (null == userDao.findByMobile(userWrapper.getMobile())) {
            User user = new User(userWrapper.getMobile(), userWrapper.getUsername(), userWrapper.getPassword());
            userDao.save(user);
            authorizationDao.save(new Authorization(user, role));
            return true;
        } else {
            return false;
        }
    }

    public boolean userMobileExists(long userMobile) {
        User user = userDao.findByMobile(userMobile);
        return user != null;
    }
    
    public boolean userExists(int id){
        return userDao.findOne(id) != null;
    }

    public boolean userIsValid(User user) {
        boolean valid = true;
        if (user == null) {
            valid = false;
        } else {
            if (user.getAddress() == null || user.getDni() == null || user.getEmail() == null) {
                valid = false;
            } 
        }
        return valid;
    }
    
    public UserDetailsWrapper findUserByMobilePhone(long mobilePhone) {
        User user = userDao.findByMobile(mobilePhone);
        return entityToWrapper(user);
    }

    public List<UserDetailsWrapper> findAllUsers() {
        List<UserDetailsWrapper> userDetailsWrappers = new ArrayList<>();
        for(User user : userDao.findAll()){
            userDetailsWrappers.add(entityToWrapper(user));
        }
        return userDetailsWrappers;
    }
    
    private UserDetailsWrapper entityToWrapper(User user){
        UserDetailsWrapper userDetailsWrapper = new UserDetailsWrapper();
        userDetailsWrapper.setUsername(user.getUsername());
        userDetailsWrapper.setDni(user.getDni());
        userDetailsWrapper.setEmail(user.getEmail());
        userDetailsWrapper.setMobile(user.getMobile());
        userDetailsWrapper.setAddress(user.getAddress());
        return userDetailsWrapper;
    }

    public void updateUser(UserUpdateWrapper userUpdateWrapper) {
        User user = userDao.findOne(userUpdateWrapper.getId());
        user.setAddress(userUpdateWrapper.getAddress());
        user.setDni(userUpdateWrapper.getDni());
        user.setEmail(userUpdateWrapper.getEmail());
        user.setUsername(userUpdateWrapper.getUsername());
        user.setMobile(userUpdateWrapper.getMobile());
        userDao.save(user);
    }
    
}

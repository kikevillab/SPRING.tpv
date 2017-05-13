package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import daos.users.AuthorizationDao;
import daos.users.UserDao;
import entities.users.Authorization;
import entities.users.Role;
import entities.users.User;
import wrappers.UserRegistrationWrapper;
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

    public Page<UserWrapper> getAll(Pageable pageable,Role role) {
        Page<User> page = userDao.findAllAndRole(pageable,role);
        List<UserWrapper> userWrappers = new ArrayList<>();
        for (User user : page.getContent()) {

            userWrappers.add(new UserWrapper(user));
        }

        return new PageImpl<UserWrapper>(userWrappers, pageable, page.getTotalElements());
    }
    
    public UserWrapper getByMobileAndRole (long userMobile,Role role){
        User user = userDao.findByMobileAndRole(role,userMobile);
        UserWrapper userRetorno= null;
        if (user!=null)
         userRetorno= new UserWrapper(user);
        return userRetorno;
    }
    
    public UserWrapper getByEmailAndRole (String email, Role role){
        User user = userDao.findByEmailAndRole(role,email);
        UserWrapper userRetorno= null;
        if (user!=null)
         userRetorno= new UserWrapper(user);
        return userRetorno;
    }
    
    public UserWrapper getByDniAndRole(String dni, Role role){
        User user = userDao.findByDniAndRole(role,dni);
        UserWrapper userRetorno= null;
        if (user!=null)
         userRetorno= new UserWrapper(user);
        return userRetorno;
    }
    
    public boolean registrationUser(UserRegistrationWrapper userRegistrationWrapper, Role role) {
        if (null == userDao.findByMobile(userRegistrationWrapper.getMobile())) {
            User user = new User(userRegistrationWrapper.getMobile(), userRegistrationWrapper.getUsername(), userRegistrationWrapper.getPassword());
            userDao.save(user);
            authorizationDao.save(new Authorization(user, role));
            return true;
        } else {
            return false;
        }
    }
}

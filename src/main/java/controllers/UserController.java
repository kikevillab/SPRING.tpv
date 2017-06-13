package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import api.exceptions.NotFoundTicketReferenceException;
import api.exceptions.NotFoundUserMobileException;
import daos.users.AuthorizationDao;
import daos.users.UserDao;
import entities.users.Authorization;
import entities.users.Role;
import entities.users.User;
import wrappers.UserDetailsWrapper;
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
        if ((userWrapper == null) || (role == null))
            return false;
        if (null == userDao.findByMobile(userWrapper.getMobile())) {
            User user = new User(userWrapper.getMobile(), userWrapper.getUsername(), userWrapper.getDni(), userWrapper.getAddress(),
                    userWrapper.getEmail(), userWrapper.getPassword(), userWrapper.isActive());
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

    public boolean userExists(long userMobile) {
        return userExists(userDao.findByMobile(userMobile));
    }

    public boolean userExists(int id) {
        return userExists(userDao.findOne(id));
    }

    private boolean userExists(User user) {

        return user != null;
    }

    public Page<UserWrapper> getAllAndRole(Pageable pageable, Role role) {
        Page<User> page = userDao.findAllAndRole(pageable, role);
        List<UserWrapper> userWrappers = new ArrayList<>();
        for (User user : page.getContent()) {
            userWrappers.add(new UserWrapper(user));
        }

        return new PageImpl<UserWrapper>(userWrappers, pageable, page.getTotalElements());
    }

    public UserWrapper getByMobileAndRole(long userMobile, Role role) {
        User user = userDao.findByMobileAndRole(role, userMobile);
        UserWrapper userRetorno = null;
        if (user != null)
            userRetorno = new UserWrapper(user);
        return userRetorno;
    }

    public UserWrapper getByEmailAndRole(String email, Role role) {
        User user = userDao.findByEmailAndRole(role, email);
        UserWrapper userRetorno = null;
        if (user != null)
            userRetorno = new UserWrapper(user);
        return userRetorno;
    }

    public UserWrapper getByDniAndRole(String dni, Role role) {
        User user = userDao.findByDniAndRole(role, dni);
        UserWrapper userRetorno = null;
        if (user != null)
            userRetorno = new UserWrapper(user);
        return userRetorno;
    }

    public boolean userIsValid(User user) {
        boolean valid = true;
        if (!userExists(user)) {
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
        for (User user : userDao.findAll()) {
            userDetailsWrappers.add(entityToWrapper(user));
        }
        return userDetailsWrappers;
    }

    private UserDetailsWrapper entityToWrapper(User user) {
        UserDetailsWrapper userDetailsWrapper = new UserDetailsWrapper();
        userDetailsWrapper.setUsername(user.getUsername());
        userDetailsWrapper.setDni(user.getDni());
        userDetailsWrapper.setEmail(user.getEmail());
        userDetailsWrapper.setMobile(user.getMobile());
        userDetailsWrapper.setAddress(user.getAddress());
        return userDetailsWrapper;
    }

    public void updateUser(UserWrapper UserWrapper) {
        User user = userDao.findOne(UserWrapper.getId());
        user.setAddress(UserWrapper.getAddress());
        user.setDni(UserWrapper.getDni());
        user.setEmail(UserWrapper.getEmail());
        user.setUsername(UserWrapper.getUsername());
        user.setMobile(UserWrapper.getMobile());
        user.setActive(UserWrapper.isActive());
        userDao.save(user);
    }

    public void deleteUser(long mobile) throws NotFoundUserMobileException {
        User user = this.userDao.findByMobile(mobile);

        if (user == null)
            throw new NotFoundUserMobileException();

        for (Authorization auth : this.authorizationDao.findAll())
            if (auth.getId() == user.getId())
                this.authorizationDao.delete(auth);

        this.userDao.delete(user);
    }
    
    public UserWrapper getByTicketReference(String ticketReference) throws NotFoundTicketReferenceException {
        User user = this.userDao.findByTicketReference(ticketReference);

        if (user == null)
            throw new NotFoundTicketReferenceException();

        return new UserWrapper(user);
    }

}

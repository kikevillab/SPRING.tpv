package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.AlreadyExistUserFieldException;
import api.exceptions.InvalidUserFieldException;
import api.exceptions.NotFoundUserIdException;
import api.exceptions.NotFoundUserMobileException;
import controllers.UserController;
import entities.users.Role;
import wrappers.UserDetailsWrapper;
import wrappers.UserUpdateWrapper;
import wrappers.UserWrapper;

@RestController
@RequestMapping(Uris.VERSION)
public class UserResource {

    private UserController userController;

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @RequestMapping(value = Uris.USERS, method = RequestMethod.POST)
    public void userRegistration(@RequestBody UserWrapper userWrapper) throws InvalidUserFieldException, AlreadyExistUserFieldException {
        validateField(userWrapper.getUsername(), "username");
        if (!this.userController.registration(userWrapper, Role.MANAGER)) {
            throw new AlreadyExistUserFieldException();
        }
    }

    @RequestMapping(value = Uris.CUSTOMERS, method = RequestMethod.POST)
    public void customerRegistration(@RequestBody UserWrapper userWrapper)
            throws InvalidUserFieldException, AlreadyExistUserFieldException {
        validateField(userWrapper.getUsername(), "username");
        if (!this.userController.registration(userWrapper, Role.CUSTOMER)) {
            throw new AlreadyExistUserFieldException();
        }
    }

    private void validateField(String field, String msg) throws InvalidUserFieldException {
        if (field == null || field.isEmpty()) {
            throw new InvalidUserFieldException(msg);
        }
    }
    
    @RequestMapping(value = Uris.USERS + Uris.PHONE, method = RequestMethod.GET)
    public UserDetailsWrapper findUserByMobilePhone(@PathVariable long mobilePhone) throws NotFoundUserMobileException{
        if(!userController.userExists(mobilePhone)){
            throw new NotFoundUserMobileException();
        }
        return userController.findUserByMobilePhone(mobilePhone);
    }
    
    @RequestMapping(value = Uris.USERS, method = RequestMethod.GET)
    public List<UserDetailsWrapper> findAllUsers(){
        return userController.findAllUsers();
    }
    
    @RequestMapping(value = Uris.USERS, method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserUpdateWrapper userUpdateWrapper) throws NotFoundUserIdException{
        if(!userController.userExists(userUpdateWrapper.getId())){
            throw new NotFoundUserIdException("User id: " + userUpdateWrapper.getId());
        }
        userController.updateUser(userUpdateWrapper);
    }

}

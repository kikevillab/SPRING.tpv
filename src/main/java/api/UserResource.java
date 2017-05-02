package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.AlreadyExistUserFieldException;
import api.exceptions.InvalidUserFieldException;
import controllers.UserController;
import entities.users.Role;
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

}

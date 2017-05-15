package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import api.exceptions.AlreadyExistUserFieldException;
import api.exceptions.InvalidUserFieldException;
import controllers.UserController;
import entities.users.Role;
import wrappers.UserPageWrapper;
import wrappers.UserWrapper;

@RequestMapping(Uris.VERSION + Uris.OPERATORS)
public class OperatorsResource {
    private UserController userController;

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserWrapper> userList(Pageable pageable) {
        return userController.getAllAndRole(pageable,Role.OPERATOR);
    }

    @RequestMapping(value = Uris.MOBILE + Uris.USER_MOBILE, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userMobile(@PathVariable(value = "mobile") long userMobile) {
        return userController.getByMobileAndRole(userMobile,Role.OPERATOR);
    }

    @RequestMapping(value = Uris.IDENTIFICATION + Uris.USER_IDENTIFICATION, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userIdentificacion(@PathVariable(value = "identification") String identification) {
        return userController.getByDniAndRole(identification,Role.OPERATOR);
    }

    @RequestMapping(value = Uris.EMAIL + Uris.USER_EMAIL, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userEmail(@PathVariable(value = "email") String email) {
        return userController.getByEmailAndRole(email,Role.OPERATOR);
    }
    
    @RequestMapping(value = Uris.CUSTOMERS, method = RequestMethod.POST)
    public void customerRegistration(@RequestBody UserWrapper userPageWrapper)
            throws InvalidUserFieldException, AlreadyExistUserFieldException {
        if (!this.userController.registration(userPageWrapper, Role.OPERATOR)) {
            throw new AlreadyExistUserFieldException();
        }
    }

}

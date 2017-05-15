package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
public class CustomersResource {

    private UserController userController;

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @RequestMapping(value = Uris.SEARCH, method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    public Page<UserWrapper> userList(Pageable pageable) throws InvalidUserFieldException {
        this.validateFieldObject(pageable, "Pageable: objeto para paginar");
        return userController.getAllAndRole(pageable,Role.CUSTOMER);
    }

    @RequestMapping(value = Uris.CUSTOMERS + Uris.USER_MOBILE, method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userMobile(@PathVariable long userMobile) throws InvalidUserFieldException {
        this.validateFieldObject(userMobile,"userMobile");
        return userController.getByMobileAndRole(userMobile,Role.CUSTOMER);
    }

    @RequestMapping(value = Uris.IDENTIFICATION + Uris.USER_IDENTIFICATION, method = RequestMethod.GET)
   // @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userIdentificacion(@PathVariable(value = "identification") String identification) throws InvalidUserFieldException {
        this.validateField(identification, "identification:dni");
        return userController.getByDniAndRole(identification,Role.CUSTOMER);
    }

    @RequestMapping(value = Uris.EMAIL + Uris.USER_EMAIL, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userEmail(@PathVariable(value = "email") String email) throws InvalidUserFieldException {
        this.validateField(email, "email");
        return userController.getByEmailAndRole(email,Role.CUSTOMER);
    }
    
    @RequestMapping(value = Uris.CUSTOMERS, method = RequestMethod.POST)
    public void customerRegistration(@RequestBody UserWrapper userWrapper)
            throws InvalidUserFieldException, AlreadyExistUserFieldException {
        this.validateFieldRegiter(userWrapper, "UserWrapper:usuario");
        if (!this.userController.registration(userWrapper, Role.CUSTOMER)) {
            throw new AlreadyExistUserFieldException();
        }
    }
    
    private void validateFieldObject (Object objeto, String msg) throws InvalidUserFieldException{
        if (objeto==null)
            throw new InvalidUserFieldException(msg);
    }

    private void validateField(String field, String msg) throws InvalidUserFieldException {
        if (field == null || field.isEmpty()) {
            throw new InvalidUserFieldException(msg);
        }
    }

    private void validateFieldRegiter(UserWrapper user, String msg) throws InvalidUserFieldException {
        if (user == null) {
            throw new InvalidUserFieldException(msg);
        }
        else{
            if ((user.getPassword()==null)||(user.getUsername()==null))
                throw new InvalidUserFieldException(msg);
        }
    }
}

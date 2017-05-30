package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.AlreadyExistUserFieldException;
import api.exceptions.InvalidUserFieldException;
import api.exceptions.NotFoundUserIdException;
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


    @RequestMapping(value = Uris.USERS, method = RequestMethod.PUT)
    public void updateUser(@RequestBody UserWrapper userWrapper) throws NotFoundUserIdException {
        if (!userController.userExists(userWrapper.getId())) {
            throw new NotFoundUserIdException("User id: " + userWrapper.getId());
        }
        userController.updateUser(userWrapper);
    }

    @RequestMapping(value = Uris.USERS , method = RequestMethod.GET)
    // @PreAuthorize("hasRole('ADMIN')")
    public Page<UserWrapper> userList(Pageable pageable, String role) throws InvalidUserFieldException {
        this.validateFieldObject(pageable, "Pageable: objeto para paginar");
        if (this.StringToRole(role) == null)
            return null;
        return userController.getAllAndRole(pageable, Role.valueOf(Role.class, role));
    }

    @RequestMapping(value = Uris.USERS + Uris.MOBILE, method = RequestMethod.GET)
    // @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userMobile(long mobile, String role) throws InvalidUserFieldException {
        this.validateFieldObject(mobile, "mobile");
        if (this.StringToRole(role) == null)
            return null;
        return userController.getByMobileAndRole(mobile, Role.valueOf(Role.class, role));
    }

    @RequestMapping(value = Uris.USERS + Uris.IDENTIFICATION, method = RequestMethod.GET)
    // @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userIdentificacion(String identification, String role) throws InvalidUserFieldException {
        this.validateField(identification, "identification:dni");
        if (this.StringToRole(role) == null)
            return null;
        return userController.getByDniAndRole(identification, Role.valueOf(Role.class, role));
    }

    @RequestMapping(value = Uris.USERS +Uris.EMAIL, method = RequestMethod.GET)
    // @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userEmail(@RequestParam(value = "email") String email, String role) throws InvalidUserFieldException {
        this.validateField(email, "email");
        if (this.StringToRole(role) == null)
            return null;
        return userController.getByEmailAndRole(email, Role.valueOf(Role.class, role));
    }

    @RequestMapping(value = Uris.USERS , method = RequestMethod.POST)
    public void customerRegistration(@RequestBody UserWrapper userWrapper, String role)
            throws InvalidUserFieldException, AlreadyExistUserFieldException {
        this.validateFieldRegiter(userWrapper, "UserWrapper:usuario");
        validateField(userWrapper.getUsername(), "username");
        if (this.StringToRole(role) == null)
            return;
        if (!this.userController.registration(userWrapper, Role.valueOf(Role.class, role))) {
            throw new AlreadyExistUserFieldException();
        }
    }

    private void validateFieldObject(Object objeto, String msg) throws InvalidUserFieldException {
        if (objeto == null)
            throw new InvalidUserFieldException(msg);
    }

    private void validateFieldRegiter(UserWrapper user, String msg) throws InvalidUserFieldException {
        if (user == null) {
            throw new InvalidUserFieldException(msg);
        } else {
            if ((user.getPassword() == null) || (user.getUsername() == null))
                throw new InvalidUserFieldException(msg);
        }
    }

    private void validateField(String field, String msg) throws InvalidUserFieldException {
        if (field == null || field.isEmpty()) {
            throw new InvalidUserFieldException(msg);
        }
    }

    private Role StringToRole(String roleParam) {
        Role rolRetorno = null;
        try {
            rolRetorno = Role.valueOf(Role.class, roleParam);
            return rolRetorno;
        } catch (Exception e) {
            return rolRetorno;
        }

    }

}

package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.AlreadyExistUserFieldException;
import api.exceptions.InvalidUserFieldException;
import controllers.UserController;
import entities.users.Role;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import wrappers.UserWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.OPERATORS)
public class OperatorsResource {
    private UserController userController;

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @ApiOperation(value = "Find partners")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported.")
    })
    @RequestMapping(method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    public Page<UserWrapper> userList(Pageable pageable) throws InvalidUserFieldException {
        this.validateFieldObject(pageable, "Pageable: objeto para paginar");
        return userController.getAllAndRole(pageable,Role.OPERATOR);
    }

    @RequestMapping(value = Uris.MOBILE+ Uris.USER_MOBILE, method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userMobile(@PathVariable long mobile) throws InvalidUserFieldException {
        this.validateFieldObject(mobile,"mobile");
        return userController.getByMobileAndRole(mobile,Role.OPERATOR);
    }
    
   

    @RequestMapping(value = Uris.IDENTIFICATION + Uris.USER_IDENTIFICATION, method = RequestMethod.GET)
   // @PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userIdentificacion(@PathVariable(value = "identification") String identification) throws InvalidUserFieldException {
        this.validateField(identification, "identification:dni");
        return userController.getByDniAndRole(identification,Role.OPERATOR);
    }

    @RequestMapping(value = Uris.EMAIL , method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    public UserWrapper userEmail(@RequestParam(value = "email") String email) throws InvalidUserFieldException {
        this.validateField(email, "email");
        return userController.getByEmailAndRole(email,Role.OPERATOR);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void customerRegistration(@RequestBody UserWrapper userWrapper)
            throws InvalidUserFieldException, AlreadyExistUserFieldException {
        this.validateFieldRegiter(userWrapper, "UserWrapper:usuario");
        if (!this.userController.registration(userWrapper, Role.OPERATOR)) {
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

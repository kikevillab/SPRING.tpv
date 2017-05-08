package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controllers.UserController;
import entities.users.User;

@RestController
@RequestMapping(Uris.VERSION+ Uris.CUSTOMERS)
public class CustomersResource {
    
    private UserController userController;
    
    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    
    @RequestMapping(value = Uris.USERS, method = RequestMethod.GET)
    public Page<User> userList(Pageable pageable){
        return userController.getAll(pageable);
    }

}

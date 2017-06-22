package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controllers.AdminController;
import controllers.RemoveTokenExpiredController;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(Uris.VERSION + Uris.ADMINS)
public class AdminResource {

    private AdminController adminController;

    private RemoveTokenExpiredController RemoveTokenExpiredController;

    @Autowired
    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @Autowired
    public void setRemoveTokenExpiredController(RemoveTokenExpiredController RemoveTokenExpiredController) {
        this.RemoveTokenExpiredController = RemoveTokenExpiredController;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String version(String param) {
        return "{\"version\":\"" + Uris.VERSION + "\"}";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllExceptAdmin() {
        adminController.deleteAllExceptAdmin();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void seedDatabase() {
        adminController.seedDatabase();
    }

    @ApiOperation(value = "Remove Token expired",

            notes = "If response is true then remove all tokens expired else there are not tokens expired", response = Boolean.class)
    @RequestMapping(value = Uris.DELETE_TOKEN_EXPIRED, method = RequestMethod.DELETE)
    public Boolean EliminarTokensCaducados() {
        int retorno = RemoveTokenExpiredController.removeTokenExpired();
        if (retorno == 0)
            return false;
        else
            return true;

    }

}

package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.FileNameNotFoundException;
import controllers.AdminController;

@RestController
@RequestMapping(Uris.VERSION + Uris.ADMINS)
public class AdminResource {

    private AdminController adminController;

    @Autowired
    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String version(String param) {
        return "{\"version\":\"" + Uris.VERSION + "\"}";
    }

    @RequestMapping(value = Uris.ADMINS_DATABASE, method = RequestMethod.POST)
    public void seedDatabase(@RequestBody(required = false) String ymlFileName) throws FileNameNotFoundException {
        if (ymlFileName == null) {
            adminController.seedDatabase();
        } else {
            if (!adminController.existsYamlFile(ymlFileName)) {
                throw new FileNameNotFoundException();
            } else {
                adminController.seedDatabase(ymlFileName);
            }
        }
    }
    
    @RequestMapping(value = Uris.ADMINS_DATABASE + Uris.ADMINS_DATABASE_VARIOUS, method = RequestMethod.POST)
    public void seedDatabaseVarious() {
        adminController.seedDatabaseVarious();
    }

    @RequestMapping(value = Uris.ADMINS_DATABASE, method = RequestMethod.DELETE)
    public void deleteAllExceptAdmin() {
        adminController.deleteAllExceptAdmin();
    }
}

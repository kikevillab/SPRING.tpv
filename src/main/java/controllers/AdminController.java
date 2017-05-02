package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import services.DatabaseSeederService;

@Controller
public class AdminController {

    private DatabaseSeederService databaseSeederService;

    @Autowired
    public void setDatabaseSeederService(DatabaseSeederService databaseSeederService) {
        this.databaseSeederService = databaseSeederService;
    }

    public void deleteAllExceptAdmin() {
        databaseSeederService.deleteAllExceptAdmin();
    }
    
    public void seedDatabase(){
        databaseSeederService.seed();
    }

}

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import services.DatabaseSeederService;

@Controller
public class AdminController {

    private DatabaseSeederService dataService;

    @Autowired
    public void setDataService(DatabaseSeederService dataService) {
        this.dataService = dataService;
    }

    public void deleteAllExceptAdmin() {
        dataService.deleteAllExceptAdmin();
    }

}

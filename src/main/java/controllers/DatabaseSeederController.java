package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import services.DatabaseSeederService;

@Controller
public class DatabaseSeederController {

    private DatabaseSeederService databaseSeederService;

    @Autowired
    public void setSeedService(DatabaseSeederService databaseSeederService) {
        this.databaseSeederService = databaseSeederService;
    }
    
    public boolean existsYamlFile(String fileName) {
        return databaseSeederService.existsYamlFile(fileName);
    }
    
    public void seedDatabase(String fileName) {
        databaseSeederService.seedDatabase(fileName);
    }
}

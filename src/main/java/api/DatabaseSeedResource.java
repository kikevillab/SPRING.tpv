package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.exceptions.NotFoundYamlFileException;
import controllers.DatabaseSeederController;
import wrappers.FileNameWrapper;

@RestController
@RequestMapping(Uris.VERSION + Uris.DATABASE_SEED)
public class DatabaseSeedResource {

    private DatabaseSeederController databaseSeederController;

    @Autowired
    public void setSeedController(DatabaseSeederController seedController) {
        this.databaseSeederController = seedController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void seedDatabase(@RequestBody FileNameWrapper fileNameWrapper) throws NotFoundYamlFileException {
        String fileName = fileNameWrapper.getFileName();
        if (!databaseSeederController.existsYamlFile(fileName)) {
            throw new NotFoundYamlFileException();
        } else {
            databaseSeederController.seedDatabase(fileName);
        }
    }
}

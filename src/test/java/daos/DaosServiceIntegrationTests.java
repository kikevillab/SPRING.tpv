package daos;

import static config.ResourceNames.DEFAULT_SEED_FILE;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.DatabaseSeederService;

@Service
public class DaosServiceIntegrationTests {

    @Autowired
    private DatabaseSeederService dataService;

    @PostConstruct
    public void populate() {
        dataService.seedDatabase(DEFAULT_SEED_FILE);
    }

    public void deleteAll() {
        dataService.deleteAllExceptAdmin();
    }
}

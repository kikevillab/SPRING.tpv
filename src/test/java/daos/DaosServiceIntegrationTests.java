package daos;

import static config.ResourceNames.TEST_SEED_FILE;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.DatabaseSeederService;

@Service
public class DaosServiceIntegrationTests {

    @Autowired
    private DatabaseSeederService databaseSeederService;

    @PostConstruct
    public void populate() {
        databaseSeederService.seedDatabase(TEST_SEED_FILE);
    }
}

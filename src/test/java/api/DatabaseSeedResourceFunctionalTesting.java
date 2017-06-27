package api;

import static config.ResourceNames.TEST_SEED_YAML_FILE_NAME;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;
import wrappers.FileNameWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class DatabaseSeedResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testNonexistentFile() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.DATABASE_SEED).body(new FileNameWrapper("nonexistent.yml"))
                .basicAuth(restService.loginAdmin(), "").post().build();
    }

    @Test
    public void testSeedDatabase() {
        restService.deleteAllExceptAdmin();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.DATABASE_SEED).body(new FileNameWrapper(TEST_SEED_YAML_FILE_NAME))
                .basicAuth(restService.loginAdmin(), "").post().build();
    }

}

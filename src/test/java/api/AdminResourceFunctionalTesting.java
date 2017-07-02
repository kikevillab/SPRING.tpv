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
public class AdminResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSeedDatabaseFileNotFound() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.ADMINS).path(Uris.DATABASE).body(new FileNameWrapper("nonExistent.yml"))
                .basicAuth(restService.loginAdmin(), "").post().build();
    }

    @Test
    public void testSeedDatabase() {
        restService.deleteAllExceptAdmin();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.ADMINS).path(Uris.DATABASE)
                .body(TEST_SEED_YAML_FILE_NAME).basicAuth(restService.loginAdmin(), "").post().build();
    }

}

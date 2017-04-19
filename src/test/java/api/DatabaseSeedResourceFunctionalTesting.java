package api;

import static config.ResourceNames.DEFAULT_SEED_FILE;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;

import wrappers.FileNameWrapper;

public class DatabaseSeedResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        new RestService().deleteAll();
    }

    @Test
    public void testNonexistentFile() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        new RestBuilder<Object>(RestService.URL).path(Uris.DATABASE_SEED).body(new FileNameWrapper("nonexistent.yml")).basicAuth(token, "").post()
                .build();
    }

    @Test
    public void testSeedDatabase() {
        String token = new RestService().loginAdmin();
        new RestBuilder<Object>(RestService.URL).path(Uris.DATABASE_SEED).body(new FileNameWrapper(DEFAULT_SEED_FILE)).basicAuth(token, "").post()
                .build();
    }

    @Test
    public void testSeedDatabaseUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        new RestBuilder<Object>(RestService.URL).path(Uris.DATABASE_SEED).body(new FileNameWrapper(DEFAULT_SEED_FILE)).post().build();
    }

    @Test
    public void testSeedDatabaseForbidden() {
        thrown.expect(new HttpMatcher(HttpStatus.FORBIDDEN));
        String token = new RestService().registerAndLoginManager();
        new RestBuilder<Object>(RestService.URL).path(Uris.DATABASE_SEED).body(new FileNameWrapper(DEFAULT_SEED_FILE)).basicAuth(token, "").post()
                .build();
    }

    @After
    public void tearDown() {
        new RestService().deleteAll();
    }
}

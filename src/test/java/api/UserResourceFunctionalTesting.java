package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import wrappers.UserDetailsWrapper;
import wrappers.UserWrapper;

public class UserResourceFunctionalTesting {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateManager() {
        String token = new RestService().loginAdmin();
        for (int i = 0; i < 4; i++) {
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(new UserWrapper(777000000 + i, "user" + i, "pass"))
                    .basicAuth(token, "").post().build();
        }
    }

    @Test
    public void testCreateManagerUnauthorized() {
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(new UserWrapper(667000000, "user", "pass")).post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            LogManager.getLogger(this.getClass())
                    .info("testCreateManagerUnauthorized (" + httpError.getMessage() + "):\n     " + httpError.getResponseBodyAsString());
        }
    }

    @Test
    public void testBadRequestCreate() {
        String token = new RestService().loginAdmin();
        try {
            UserWrapper userWrapper = new UserWrapper(0, "", "pass");
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).basicAuth(token, "").post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.BAD_REQUEST, httpError.getStatusCode());
            LogManager.getLogger(this.getClass())
                    .info("testBadRequestCreate (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }

    @Test
    public void testRepeatingFieldCreate() {
        String token = new RestService().loginAdmin();
        UserWrapper userWrapper = new UserWrapper(777000000, "user", "pass");
        new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).basicAuth(token, "").post().build();
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).basicAuth(token, "").post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.CONFLICT, httpError.getStatusCode());
            LogManager.getLogger(this.getClass())
                    .info("testRepeatingFieldCreate (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }

    @Test
    public void testCreateCustomer() {
        String token = new RestService().registerAndLoginManager();
        new RestBuilder<Object>(RestService.URL).path(Uris.CUSTOMERS).body(new UserWrapper(777000000, "customer", "pass"))
                .basicAuth(token, "").post().build();
    }

    @Test
    public void testCreateCustomerForbidden() {
        try {
            String token = new RestService().loginAdmin();
            new RestBuilder<Object>(RestService.URL).path(Uris.CUSTOMERS).body(new UserWrapper(777000000, "customer", "pass"))
                    .basicAuth(token, "").post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.FORBIDDEN, httpError.getStatusCode());
            LogManager.getLogger(this.getClass())
                    .info("testCreateCustomerForbidden (" + httpError.getMessage() + "):\n " + httpError.getResponseBodyAsString());
        }
    }

    @Test
    public void testFindUserByMobilePhoneWithNonExistentPhone() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        String mobilePhone = "555000000";
        new RestBuilder<UserDetailsWrapper>(RestService.URL).path(Uris.USERS).pathId(mobilePhone).basicAuth(token, "").clazz(UserDetailsWrapper.class).get().build();
    }
    
    @Test
    public void testFindUserByMobilePhoneWithExistentPhone() {
        String token = new RestService().loginAdmin();
        String mobilePhone = String.valueOf(123456789L);
        System.out.println(mobilePhone);
        UserDetailsWrapper user = new RestBuilder<UserDetailsWrapper>(RestService.URL).path(Uris.USERS).pathId(mobilePhone).basicAuth(token, "").clazz(UserDetailsWrapper.class).get().build();
        assertNotNull(user);
    }

    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }
}

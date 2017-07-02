package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;
import wrappers.UserDetailsWrapper;
import wrappers.UserPageWrapper;
import wrappers.UserWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class UserResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateDelete() {
        new RestBuilder<Object>(restService.getUrl()).path(Uris.USERS).body(new UserWrapper(777700000, "userApi", "pass"))
                .param("role", "MANAGER").basicAuth(restService.loginAdmin(), "").post().build();
        new RestBuilder<String>(restService.getUrl()).path(Uris.USERS + "/" + 777700000).clazz(String.class)
                .basicAuth(restService.loginAdmin(), "").delete().build();
    }

    //@Test
    public void testCreateUnauthorized() {
        thrown.expect(new HttpMatcher(HttpStatus.UNAUTHORIZED));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.USERS).body(new UserWrapper(667000000, "user", "pass"))
                .param("role", "MANAGER").post().build();
    }

    @Test
    public void testBadRequestCreate() {
        thrown.expect(new HttpMatcher(HttpStatus.BAD_REQUEST));
        UserWrapper userWrapper = new UserWrapper(0, "", "pass");
        new RestBuilder<Object>(restService.getUrl()).path(Uris.USERS).body(userWrapper).param("role", "MANAGER")
                .basicAuth(restService.loginAdmin(), "").post().build();
    }

    @Test
    public void testRepeatingFieldCreate() {
        UserWrapper userWrapper = new UserWrapper(777000000, "user", "pass");
        new RestBuilder<Object>(restService.getUrl()).path(Uris.USERS).body(userWrapper).param("role", "MANAGER")
                .basicAuth(restService.loginAdmin(), "").post().build();
        thrown.expect(new HttpMatcher(HttpStatus.CONFLICT));
        new RestBuilder<Object>(restService.getUrl()).path(Uris.USERS).body(userWrapper).param("role", "MANAGER")
                .basicAuth(restService.loginAdmin(), "").post().build();
        new RestBuilder<String>(restService.getUrl()).path(Uris.USERS + "/" + 777700000).clazz(String.class)
                .basicAuth(restService.loginAdmin(), "").delete().build();
    }

    // TODO revisar el wrapper
    @Test
    public void testUpdateUserWithNonExistentUser() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setId(0);
        userWrapper.setAddress("address");
        userWrapper.setDni("123456789");
        userWrapper.setEmail("test@test.com");
        userWrapper.setMobile(123456789);
        userWrapper.setUsername("username");
        userWrapper.setPassword("pass");
        userWrapper.setActive(true);
        new RestBuilder<Object>(restService.getUrl()).path(Uris.USERS).body(userWrapper).basicAuth(restService.loginAdmin(), "")
                .clazz(Object.class).put().build();
    }

    @Test
    public void testUserList() {
        UserPageWrapper userPage = new RestBuilder<UserPageWrapper>(restService.getUrl()).path(Uris.USERS).param("size", "4")
                .param("page", "0").param("role", "CUSTOMER").basicAuth(restService.loginAdmin(), "").clazz(UserPageWrapper.class).get()
                .build();
        assertNotNull(userPage);

    }

    @Test
    public void testUserMobile() {
        UserWrapper userMobile = new RestBuilder<UserWrapper>(restService.getUrl()).path(Uris.USERS + Uris.USER_MOBILE)
                .param("mobile", "666000003").param("role", "CUSTOMER").basicAuth(restService.loginAdmin(), "").clazz(UserWrapper.class)
                .get().build();
        assertNotNull(userMobile);

    }

    @Test
    public void testUserIdentificacion() {
        UserWrapper userIdentifaction = new RestBuilder<UserWrapper>(restService.getUrl()).path(Uris.USERS + Uris.USER_IDENTIFICATION)
                .param("identification", "1235678X").param("role", "CUSTOMER").basicAuth(restService.loginAdmin(), "")
                .clazz(UserWrapper.class).get().build();
        assertNotNull(userIdentifaction);

    }

    @Test
    public void testUserEmail() {
        UserWrapper userEmail = new RestBuilder<UserWrapper>(restService.getUrl()).path(Uris.USERS + Uris.USER_EMAIL)
                .param("email", "user@user.com").param("role", "CUSTOMER").basicAuth(restService.loginAdmin(), "").clazz(UserWrapper.class)
                .get().build();
        assertNotNull(userEmail);

    }

    @Test
    public void testFindUserByMobilePhoneWithNonExistentPhone() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<UserDetailsWrapper>(restService.getUrl()).path(Uris.USERS).pathId(555000000).basicAuth(restService.loginAdmin(), "")
                .clazz(UserDetailsWrapper.class).get().build();
    }

    @Test
    public void testFindUserByMobilePhoneWithExistentPhone() {
        UserDetailsWrapper user = new RestBuilder<UserDetailsWrapper>(restService.getUrl()).path(Uris.USERS).pathId("123456789")
                .basicAuth(restService.loginAdmin(), "").clazz(UserDetailsWrapper.class).get().build();
        assertNotNull(user);
    }

    @Test
    public void testFindUserByTicketReference() {
        UserWrapper user = new RestBuilder<UserWrapper>(restService.getUrl()).path(Uris.USERS + Uris.TICKET_REFERENCE).path("/ticket2")
                .basicAuth(restService.loginAdmin(), "").clazz(UserWrapper.class).get().build();
        assertEquals(666000003, user.getMobile());
    }

    @Test
    public void testFindUserByTicketReferenceWithNonExistentTicketReference() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<UserWrapper>(restService.getUrl()).path(Uris.USERS + Uris.TICKET_REFERENCE).path("/dfjakdlj78987")
                .basicAuth(restService.loginAdmin(), "").clazz(UserWrapper.class).get().build();
    }

}

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
import org.springframework.web.client.HttpClientErrorException;

import config.TestsApiConfig;
import wrappers.UserDetailsWrapper;
import wrappers.UserWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class UserResourceFunctionalTesting {

    @Autowired
    private RestService restService;

    private static final String WRONG_TICKET_REFERENCE = "dfjakdlj78987";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateDelete() {
        String token = restService.loginAdmin();
        new RestBuilder<Object>(restService.getUrl()).path(Uris.USERS).body(new UserWrapper(777700000, "userApi", "pass"))
                .param("role", "MANAGER").basicAuth(token, "").post().build();
        new RestBuilder<String>(restService.getUrl()).path(Uris.USERS + "/" + 777700000).clazz(String.class).basicAuth(token, "").delete()
                .build();
    }

    @Test
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

    /*
     * @Test public void testUpdateUserWithNonExistentUser() { thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND)); String token = new
     * RestService().loginAdmin(); UserWrapper userWrapper = new UserWrapper(); userWrapper.setId(0); userWrapper.setAddress("address");
     * userWrapper.setDni("123456789"); userWrapper.setEmail("test@test.com"); userWrapper.setMobile(123456789);
     * userWrapper.setUsername("username"); userWrapper.setPassword("pass"); userWrapper.setActive(true); new
     * RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).basicAuth(token, "").clazz(Object.class).put().build(); }
     * 
     * @Test public void testUserList() { String token = new RestService().loginAdmin();
     * 
     * UserPageWrapper userPage = new RestBuilder<UserPageWrapper>(RestService.URL).path(Uris.USERS).param("size", "4").param("page", "0")
     * .param("role", "CUSTOMER").basicAuth(token, "").clazz(UserPageWrapper.class).get().build(); assertNotNull(userPage);
     * 
     * }
     */
    // TODO Falla en maven
    // @Test
    // public void testUserMobile() {
    // String token = new RestService().loginAdmin();
    // String mobile = String.valueOf(666000003);
    // UserWrapper userMobile = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.MOBILE).param("mobile", mobile)
    // .param("role", "CUSTOMER").basicAuth(token, "").clazz(UserWrapper.class).get().build();
    // assertNotNull(userMobile);
    //
    // }
    // TODO Falla en maven
    // @Test
    // public void testUserIdentificacion() {
    // String token = new RestService().loginAdmin();
    // String identification = "1235678X";
    // UserWrapper userIdentifaction = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.IDENTIFICATION)
    // .param("identification", identification).param("role", "CUSTOMER").basicAuth(token, "").clazz(UserWrapper.class).get()
    // .build();
    // assertNotNull(userIdentifaction);
    //
    // }
    // TODO Falla en maven
    // @Test
    // public void testUserEmail() {
    // String token = new RestService().loginAdmin();
    // String email = "user@user.com";
    // UserWrapper userEmail = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.EMAIL).param("email", email)
    // .param("role", "CUSTOMER").basicAuth(token, "").clazz(UserWrapper.class).get().build();
    //
    // assertNotNull(userEmail);
    //
    // }

    @Test
    public void testFindUserByMobilePhoneWithNonExistentPhone() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        new RestBuilder<UserDetailsWrapper>(restService.getUrl()).path(Uris.USERS).pathId(555000000).basicAuth(restService.loginAdmin(), "")
                .clazz(UserDetailsWrapper.class).get().build();
    }


    public void testFindUserByMobilePhoneWithExistentPhone() {
        String token = new RestService().loginAdmin();
        String mobilePhone = String.valueOf(123456789L);
        UserDetailsWrapper user = new RestBuilder<UserDetailsWrapper>(RestService.URL).path(Uris.USERS).pathId(mobilePhone)
                .basicAuth(token, "").clazz(UserDetailsWrapper.class).get().build();
        assertNotNull(user);
    }


    /*
     * @Test public void testGetByTicketReference() { String reference="ticket2"; UserWrapper user = new
     * RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS+Uris.TICKET_REFERENCE) .path("/" +
     * reference).clazz(UserWrapper.class).get().build(); assertEquals(666000003, user.getMobile()); }
     */


    public void testGetByTicketReferenceException() {
        try {
            new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.TICKET_REFERENCE).path("/" + WRONG_TICKET_REFERENCE)
                    .clazz(UserWrapper.class).get().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
        }
    }

}

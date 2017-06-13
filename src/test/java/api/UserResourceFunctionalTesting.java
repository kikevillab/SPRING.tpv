package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import wrappers.UserDetailsWrapper;
import wrappers.UserPageWrapper;
import wrappers.UserWrapper;

public class UserResourceFunctionalTesting {
    
    private static final String WRONG_TICKET_REFERENCE = "dfjakdlj78987";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreate() {
        String token = new RestService().loginAdmin();
        for (int i = 0; i < 4; i++) {
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(new UserWrapper(777700000 + i, "user2" + i, "pass"))
                    .param("role", "CUSTOMER").basicAuth(token, "").post().build();
        }

    }

    @Test
    public void testCreateUnauthorized() {
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(new UserWrapper(667000000, "user", "pass"))
                    .param("role", "CUSTOMER").post().build();
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
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).param("role", "CUSTOMER").basicAuth(token, "")
                    .post().build();
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
        new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).param("role", "CUSTOMER").basicAuth(token, "").post()
                .build();
        try {
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).param("role", "CUSTOMER").basicAuth(token, "")
                    .post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.CONFLICT, httpError.getStatusCode());
            LogManager.getLogger(this.getClass())
                    .info("testRepeatingFieldCreate (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }

    @Test
    public void testUpdateUserWithNonExistentUser() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setId(0);
        userWrapper.setAddress("address");
        userWrapper.setDni("123456789");
        userWrapper.setEmail("test@test.com");
        userWrapper.setMobile(123456789);
        userWrapper.setUsername("username");
        userWrapper.setPassword("pass");
        userWrapper.setActive(true);
        new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(userWrapper).basicAuth(token, "").clazz(Object.class).put().build();
    }

    @Test
    public void testUserList() {
        String token = new RestService().loginAdmin();

        UserPageWrapper userPage = new RestBuilder<UserPageWrapper>(RestService.URL).path(Uris.USERS).param("size", "4").param("page", "0")
                .param("role", "CUSTOMER").basicAuth(token, "").clazz(UserPageWrapper.class).get().build();
        assertNotNull(userPage);

    }
//TODO Falla en maven
//    @Test
//    public void testUserMobile() {
//        String token = new RestService().loginAdmin();
//        String mobile = String.valueOf(666000003);
//        UserWrapper userMobile = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.MOBILE).param("mobile", mobile)
//                .param("role", "CUSTOMER").basicAuth(token, "").clazz(UserWrapper.class).get().build();
//        assertNotNull(userMobile);
//
//    }
//TODO Falla en maven
//    @Test
//    public void testUserIdentificacion() {
//        String token = new RestService().loginAdmin();
//        String identification = "1235678X";
//        UserWrapper userIdentifaction = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.IDENTIFICATION)
//                .param("identification", identification).param("role", "CUSTOMER").basicAuth(token, "").clazz(UserWrapper.class).get()
//                .build();
//        assertNotNull(userIdentifaction);
//
//    }
//TODO Falla en maven
//    @Test
//    public void testUserEmail() {
//        String token = new RestService().loginAdmin();
//        String email = "user@user.com";
//        UserWrapper userEmail = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.EMAIL).param("email", email)
//                .param("role", "CUSTOMER").basicAuth(token, "").clazz(UserWrapper.class).get().build();
//
//        assertNotNull(userEmail);
//
//    }

    @Test
    public void testFindUserByMobilePhoneWithNonExistentPhone() {
        thrown.expect(new HttpMatcher(HttpStatus.NOT_FOUND));
        String token = new RestService().loginAdmin();
        String mobilePhone = "555000000";
        new RestBuilder<UserDetailsWrapper>(RestService.URL).path(Uris.USERS).pathId(mobilePhone).basicAuth(token, "")
                .clazz(UserDetailsWrapper.class).get().build();
    }

    @Test
    public void testFindUserByMobilePhoneWithExistentPhone() {
        String token = new RestService().loginAdmin();
        String mobilePhone = String.valueOf(123456789L);
        UserDetailsWrapper user = new RestBuilder<UserDetailsWrapper>(RestService.URL).path(Uris.USERS).pathId(mobilePhone)
                .basicAuth(token, "").clazz(UserDetailsWrapper.class).get().build();
        assertNotNull(user);
    }

    @Test
    public void testDeleteUser() {

        String token = new RestService().loginAdmin();
        long mobile = 766700774;
        new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(new UserWrapper(mobile, "usuarioDelete", "passDelete"))
                .param("role", "CUSTOMER").basicAuth(token, "").post().build();

        new RestBuilder<String>(RestService.URL).path(Uris.USERS + "/" + mobile).clazz(String.class).basicAuth(token, "").delete().build();

        UserWrapper userMobile = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS + Uris.MOBILE)
                .param("mobile", String.valueOf(mobile)).param("role", "CUSTOMER").basicAuth(token, "").clazz(UserWrapper.class).get()
                .build();
        assertNull(userMobile);

    }
    
    
    @Test
    public void testGetByTicketReference() {
        String reference="ticket2";       
        UserWrapper user = new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS+Uris.TICKET_REFERENCE)
                .path("/" + reference).clazz(UserWrapper.class).get().build();
        assertEquals(666000003, user.getMobile());
    }
    
    @Test
    public void testGetByTicketReferenceException() {
        try {
            new RestBuilder<UserWrapper>(RestService.URL).path(Uris.USERS+Uris.TICKET_REFERENCE).path("/" + WRONG_TICKET_REFERENCE).clazz(UserWrapper.class).get()
                    .build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
        }
    }

}

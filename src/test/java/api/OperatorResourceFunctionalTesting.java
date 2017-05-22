package api;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import wrappers.UserPageWrapper;
import wrappers.UserWrapper;

public class OperatorResourceFunctionalTesting {
    @Test
    public void  testUserList(){
        String token = new RestService().loginAdmin();

        UserPageWrapper userPage = new RestBuilder<UserPageWrapper>(RestService.URL).path(Uris.OPERATORS)
                .param("size", "4").param("page", "0").basicAuth(token, "")
                .clazz(UserPageWrapper.class).get().build();
        assertNotNull(userPage);
       
    }
    
    @Test
    public void  testUserMobile(){
        String token = new RestService().loginAdmin();
        String mobile =String.valueOf(666000004);
        UserWrapper userMobile= new RestBuilder<UserWrapper>(RestService.URL+Uris.OPERATORS).path(Uris.MOBILE)
                .pathId(mobile).basicAuth(token, "")
                .clazz(UserWrapper.class).get().build();
        assertNotNull(userMobile);
       
    }
    
    @Test
    public void  testUserIdentificacion(){
        String token = new RestService().loginAdmin();
        String identification="1245538L";
        UserWrapper userIdentifaction= new RestBuilder<UserWrapper>(RestService.URL+Uris.OPERATORS).path(Uris.IDENTIFICATION)
                .pathId(identification).basicAuth(token, "")
                .clazz(UserWrapper.class).get().build();
        assertNotNull(userIdentifaction);
       
    }
    
    @Test
    public void  testUserEmail(){
        String token = new RestService().loginAdmin();
        String email="userO@userO.com";
        UserWrapper userEmail= new RestBuilder<UserWrapper>(RestService.URL+Uris.OPERATORS).path(Uris.EMAIL)
                .param("email",email).basicAuth(token, "")
                .clazz(UserWrapper.class).get().build();

        assertNotNull(userEmail);
       
    }
    
    
    @Test
    public void  testCustomerRegistration(){      
        String tokenRegister = new RestService().registerAndLoginManager();

        new RestBuilder<Object>(RestService.URL).path(Uris.OPERATORS)
                .body(new UserWrapper(777012530, "operator", "pass1")).basicAuth(tokenRegister, "").post().build();
        
       
    }
}

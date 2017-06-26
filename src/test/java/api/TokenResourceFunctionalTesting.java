package api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.TestsApiConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestsApiConfig.class})
public class TokenResourceFunctionalTesting {
    
    @Autowired
    private RestService restService;
    
    @Test
    public void testLoginAdmin(){
        String token = restService.loginAdmin();
        assertTrue(token.length() > 20);
    }

    @Test
    public void testLoginManager() {
        String token = restService.loginManager();
        assertTrue(token.length() > 20);
    }

    @Test
    public void testLoginOperator() {
        String token = restService.loginOperator();
        assertTrue(token.length() > 20);
    }
    
}

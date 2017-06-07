package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import api.Uris;
import wrappers.TokenWrapper;
import wrappers.UserWrapper;

public class RestService {
    
    static Properties p;
    
    static {
        ClassPathResource resource = new ClassPathResource( "test.properties" );
        p = new Properties();
        InputStream inputStream = null;
        
        try {
            inputStream = resource.getInputStream();
            p.load( inputStream );
        } catch (IOException e) {}
    }

    public static final String URL = p.getProperty("tomcat.url") + Uris.VERSION;

    public void deleteAll() {
        new RestBuilder<TokenWrapper>(RestService.URL).path(Uris.ADMINS).basicAuth(this.loginAdmin(), "").delete().build();
    }

    public String loginAdmin() {
        TokenWrapper token = new RestBuilder<TokenWrapper>(URL).path(Uris.TOKENS).basicAuth("123456789", "admin").clazz(TokenWrapper.class)
                .post().build();
        return token.getToken();
    }

    public String registerAndLoginManager() {
        UserWrapper manager = new UserWrapper(666000666, "daemon", "pass");
        new RestBuilder<Object>(URL).path(Uris.USERS).body(manager).param("role", "MANAGER").basicAuth(this.loginAdmin(), "").post().build();
        TokenWrapper token = new RestBuilder<TokenWrapper>(URL).path(Uris.TOKENS)
                .basicAuth(Long.toString(manager.getMobile()), manager.getPassword()).clazz(TokenWrapper.class).post().build();
        return token.getToken();
    }
    
    public void seedDatabase() {
        new RestBuilder<TokenWrapper>(RestService.URL).path(Uris.ADMINS).basicAuth(this.loginAdmin(), "").post().build();
    }

}

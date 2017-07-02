package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import api.Uris;
import wrappers.TokenWrapper;

@Component
public class RestService {

    private String url;

    private String adminToken;

    private String managerToken;

    private String operatorToken;

    public RestService() {
        ClassPathResource resource = new ClassPathResource("META-INF/test.properties");
        Properties p = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            p.load(inputStream);
        } catch (IOException e) {
        }
        url = p.getProperty("tomcat.url") + Uris.VERSION;
        this.deleteAllExceptAdmin();
        this.seedDatabase();
    }

    public String getUrl() {
        return url;
    }

    public void deleteAllExceptAdmin() {
        new RestBuilder<>(url).path(Uris.ADMINS).path(Uris.DATABASE).basicAuth(this.loginAdmin(), "").delete().build();
        adminToken = null;
        managerToken = null;
        operatorToken = null;
    }

    public void seedDatabase() {
        new RestBuilder<>(url).path(Uris.ADMINS).path(Uris.DATABASE).basicAuth(this.loginAdmin(), "").post().build();
    }

    public String loginAdmin() {
        if (adminToken == null) {
            adminToken = new RestBuilder<TokenWrapper>(url).path(Uris.TOKENS).basicAuth("123456789", "admin").clazz(TokenWrapper.class)
                    .post().build().getToken();
        }
        return adminToken;
    }

    public String loginManager() {
        if (managerToken == null) {
            managerToken = new RestBuilder<TokenWrapper>(url).path(Uris.TOKENS).basicAuth("666000005", "pass").clazz(TokenWrapper.class)
                    .post().build().getToken();
        }
        return managerToken;
    }

    public String loginOperator() {
        if (operatorToken == null) {
            operatorToken = new RestBuilder<TokenWrapper>(url).path(Uris.TOKENS).basicAuth("666000004", "pass").clazz(TokenWrapper.class)
                    .post().build().getToken();
        }
        return operatorToken;
    }

}

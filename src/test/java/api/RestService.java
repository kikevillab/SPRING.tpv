package api;

import api.Uris;
import wrappers.TokenWrapper;
import wrappers.UserWrapper;

public class RestService {

    public static final String URL = "http://localhost:8080/SPRING.tpv.1.2.0-SNAPSHOT/api" + Uris.VERSION;

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
        new RestBuilder<Object>(URL).path(Uris.USERS).body(manager).basicAuth(this.loginAdmin(), "").post().build();
        TokenWrapper token = new RestBuilder<TokenWrapper>(URL).path(Uris.TOKENS)
                .basicAuth(Long.toString(manager.getMobile()), manager.getPassword()).clazz(TokenWrapper.class).post().build();
        return token.getToken();
    }

}

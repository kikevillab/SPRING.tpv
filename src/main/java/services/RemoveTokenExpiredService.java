package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import daos.users.TokenDao;

@Service
public class RemoveTokenExpiredService {

    private Environment environment;

    private TokenDao tokenDao;

    @Autowired
    public void setTokenDao(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public int removeTokenExpired() {
        return tokenDao.deleteByCreationDateLessThan(new Date(new Date().getTime() - Integer.parseInt(environment.getProperty("tokenTime.user"))));

    }
}

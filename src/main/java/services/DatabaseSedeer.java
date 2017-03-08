package services;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import config.ResourceNames;
import daos.users.AuthorizationDao;
import daos.users.UserDao;
import entities.users.Authorization;
import entities.users.Role;
import entities.users.User;

@Service
@Transactional
@PropertySource(ResourceNames.PROPERTIES)
public class DatabaseSedeer {

    private String adminUsername;

    private String adminEmail;

    private String adminPassword;

    private long adminMobile;

    @Autowired
    private Environment environment;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @PostConstruct
    public void readAdmin() {
        adminMobile = Long.valueOf(environment.getProperty("admin.mobile"));
        adminUsername = environment.getProperty("admin.username");
        adminEmail = environment.getProperty("admin.email");
        adminPassword = environment.getProperty("admin.password");
        createDefaultAdmin();
    }

    public void createDefaultAdmin() {
        User adminSaved = userDao.findByMobile(adminMobile);
        if (adminSaved == null) {
            User admin = new User(adminMobile, adminUsername, adminPassword);
            admin.setEmail(adminEmail);
            userDao.save(admin);
            authorizationDao.save(new Authorization(admin, Role.ADMIN));
        }
    }

}

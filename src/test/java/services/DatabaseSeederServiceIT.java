package services;

import static config.ResourceNames.DEFAULT_SEED_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import daos.core.ArticleDao;
import daos.core.EmbroideryDao;
import daos.core.InvoiceDao;
import daos.core.ProviderDao;
import daos.core.TextilePrintingDao;
import daos.core.TicketDao;
import daos.core.VoucherDao;
import daos.users.AuthorizationDao;
import daos.users.TokenDao;
import daos.users.UserDao;
import entities.core.TicketPK;
import entities.users.Role;
import entities.users.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class DatabaseSeederServiceIT {

    @Autowired
    private DatabaseSeederService databaseSeederService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private VoucherDao voucherDao;

    @Autowired
    private ProviderDao providerDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private EmbroideryDao embroideryDao;

    @Autowired
    private TextilePrintingDao textilePrintingDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Test
    public void testCreateDefaultAdmin() {
        databaseSeederService.deleteAllExceptAdmin();
        authorizationDao.deleteAll();
        tokenDao.deleteAll();
        userDao.deleteAll();

        assertEquals(0, userDao.count());

        databaseSeederService.createDefaultAdmin();

        assertEquals(1, userDao.count());
        User admin = userDao.findByUsername("admin");

        assertNotNull(admin);
        assertEquals(Role.ADMIN, authorizationDao.findRoleByUser(admin).get(0));
    }

    @Test
    public void testTpvTestDatabaseShouldBeParsed() {
        String tpvDatabaseYaml = "TPV_Test_Database.yml";
        long previousUserCount = userDao.count();
        long previousAuthorizationCount = authorizationDao.count();
        long previousTokenCount = tokenDao.count();
        long previousVoucherCount = voucherDao.count();
        long previousProvidersCount = providerDao.count();
        long previousArticlesCount = articleDao.count();
        long previousEmbroideryCount = embroideryDao.count();
        long previousTextilePrintingsNum = textilePrintingDao.count();
        long previousTicketCount = ticketDao.count();
        long previousInvoiceCount = invoiceDao.count();

        databaseSeederService.seedDatabase(tpvDatabaseYaml);

        User user = userDao.findByMobile(777000000L);
        assertNotNull(user);
        assertEquals(1, userDao.count() - previousUserCount);

        assertNotNull(authorizationDao.findRoleByUser(user));
        assertEquals(1, authorizationDao.count() - previousAuthorizationCount);

        assertNotNull(tokenDao.findByUser(user));
        assertEquals(1, tokenDao.count() - previousTokenCount);

        assertEquals(3, voucherDao.count() - previousVoucherCount);

        assertEquals(1, providerDao.count() - previousProvidersCount);

        assertNotNull(articleDao.findOne(74000001111L));
        assertEquals(1, articleDao.count() - previousArticlesCount);

        assertNotNull(embroideryDao.findOne(74000002222L));
        assertEquals(1, embroideryDao.count() - previousEmbroideryCount);

        assertNotNull(textilePrintingDao.findOne(74000003333L));
        assertEquals(1, textilePrintingDao.count() - previousTextilePrintingsNum);

        assertNotNull(ticketDao.findOne(new TicketPK(71L)));
        assertNotNull(ticketDao.findOne(new TicketPK(72L)));
        assertEquals(2, ticketDao.count() - previousTicketCount);

        assertEquals(1, invoiceDao.count() - previousInvoiceCount);
    }

    @Test
    public void testNotAllEntitiesYaml() {
        // YAML which only contains 2 users, 1 token, 1 embroidery
        // 2 textilePrintings and 1 ticket
        String notAllEntitiesYaml = "TPV_Test_Not_All_Entities.yml";
        long previousUserCount = userDao.count();
        long previousAuthorizationCount = authorizationDao.count();
        long previousTokenCount = tokenDao.count();
        long previousVoucherCount = voucherDao.count();
        long previousProvidersCount = providerDao.count();
        long previousArticlesCount = articleDao.count();
        long previousEmbroideryCount = embroideryDao.count();
        long previousTextilePrintingsNum = textilePrintingDao.count();
        long previousTicketCount = ticketDao.count();
        long previousInvoiceCount = invoiceDao.count();

        databaseSeederService.seedDatabase(notAllEntitiesYaml);

        User user1 = userDao.findByMobile(777000001L);
        assertNotNull(user1);
        User user2 = userDao.findByMobile(777000002L);
        assertNotNull(user2);
        assertEquals(2, userDao.count() - previousUserCount);

        assertTrue(authorizationDao.findRoleByUser(user1).isEmpty());
        assertTrue(authorizationDao.findRoleByUser(user2).isEmpty());
        assertEquals(authorizationDao.count(), previousAuthorizationCount);

        assertNotNull(tokenDao.findByUser(user1));
        assertNull(tokenDao.findByUser(user2));
        assertEquals(1, tokenDao.count() - previousTokenCount);

        assertEquals(voucherDao.count(), previousVoucherCount);

        assertEquals(providerDao.count(), previousProvidersCount);

        assertEquals(articleDao.count(), previousArticlesCount);

        assertNotNull(embroideryDao.findOne(74000002223L));
        assertEquals(1, embroideryDao.count() - previousEmbroideryCount);

        assertNotNull(textilePrintingDao.findOne(74000003334L));
        assertEquals(2, textilePrintingDao.count() - previousTextilePrintingsNum);

        assertNotNull(ticketDao.findOne(new TicketPK(73L)));
        assertEquals(1, ticketDao.count() - previousTicketCount);

        assertEquals(invoiceDao.count(), previousInvoiceCount);
    }

    @Test
    public void testExistentFile() {
        assertTrue(databaseSeederService.existsYamlFile(DEFAULT_SEED_FILE));
    }

    @Test
    public void testNonexistentFile() {
        assertFalse(databaseSeederService.existsYamlFile("nonexistent.yml"));
    }

    @After
    public void tearDown() {
        databaseSeederService.deleteAllExceptAdmin();
        databaseSeederService.seedDatabase(DEFAULT_SEED_FILE);
    }
}

package services;

import static config.ResourceNames.ADMIN_YAML_FILE_NAME;
import static config.ResourceNames.TEST_SEED_YAML_FILE_NAME;
import static config.ResourceNames.YAML_FILES_ROOT;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import daos.core.ArticleDao;
import daos.core.CashierClosureDao;
import daos.core.CategoryComponentDao;
import daos.core.CategoryCompositeDao;
import daos.core.EmbroideryDao;
import daos.core.InvoiceDao;
import daos.core.ProductCategoryDao;
import daos.core.ProviderDao;
import daos.core.TextilePrintingDao;
import daos.core.TicketDao;
import daos.core.VoucherDao;
import daos.users.AuthorizationDao;
import daos.users.TokenDao;
import daos.users.UserDao;
import entities.core.Article;
import entities.core.CategoryComponent;
import entities.core.CategoryComposite;
import entities.core.ProductCategory;
import entities.core.Provider;
import entities.users.Authorization;
import entities.users.Role;
import entities.users.User;

@Service
@Transactional
public class DatabaseSeederService {

    @Autowired
    private ApplicationContext appContext;

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

    @Autowired
    private CategoryComponentDao categoryComponentDao;

    @Autowired
    private CategoryCompositeDao categoryCompositeDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private CashierClosureDao cashierClosureDao;

    @PostConstruct
    public void createDefaultAdmin() {
        Yaml adminYaml = new Yaml();
        Resource resource = appContext.getResource(YAML_FILES_ROOT + ADMIN_YAML_FILE_NAME);
        InputStream input;
        try {
            input = resource.getInputStream();
            User admin = adminYaml.loadAs(input, User.class);

            User adminSaved = userDao.findByMobile(admin.getMobile());
            if (adminSaved == null) {
                userDao.save(admin);
                authorizationDao.save(new Authorization(admin, Role.ADMIN));
            }
        } catch (IOException e) {
            LogManager.getLogger(this.getClass().getSimpleName())
                    .error("File " + ADMIN_YAML_FILE_NAME + " doesn't exist or can't be opened");
        }
    }

    public void seedDatabase() {
        seedDatabase(TEST_SEED_YAML_FILE_NAME);
    }

    public void seedDatabase(String ymlFileName) {
        assert ymlFileName != null && !ymlFileName.isEmpty();
        if (!ymlFileName.equals(ADMIN_YAML_FILE_NAME)) {
            Constructor constructor = new Constructor(TpvGraph.class);
            Yaml yamlParser = new Yaml(constructor);
            Resource resource = appContext.getResource(YAML_FILES_ROOT + ymlFileName);
            InputStream input;
            try {
                input = resource.getInputStream();
                TpvGraph tpvGraph = (TpvGraph) yamlParser.load(input);
                userDao.save(tpvGraph.getUserList());
                authorizationDao.save(tpvGraph.getAuthorizationList());
                tokenDao.save(tpvGraph.getTokenList());
                voucherDao.save(tpvGraph.getVoucherList());
                providerDao.save(tpvGraph.getProviderList());
                // this.expandSize(tpvGraph);
                // this.saveProducts(tpvGraph);
                // this.expandArticlesAndProductCategory(tpvGraph.getArticleList(), tpvGraph.getProductCategoryList());
                articleDao.save(tpvGraph.getArticleList());
                embroideryDao.save(tpvGraph.getEmbroideryList());
                textilePrintingDao.save(tpvGraph.getTextilePrintingList());
                productCategoryDao.save(tpvGraph.getProductCategoryList());
                categoryCompositeDao.save(tpvGraph.getCategoryCompositeList());

                ticketDao.save(tpvGraph.getTicketList());
                invoiceDao.save(tpvGraph.getInvoiceList());
                cashierClosureDao.save(tpvGraph.getCashierClosureList());
            } catch (IOException e) {
                LogManager.getLogger(this.getClass().getSimpleName()).error("File " + ymlFileName + " doesn't exist or can't be opened");
            }
        }
    }

    protected void expandSize(TpvGraph tpvGraph) {
        List<CategoryComposite> categoryCompositeList = tpvGraph.getCategoryCompositeList();
        for (CategoryComposite categoryComposite : categoryCompositeList) {
        }
    }

    protected void saveProducts(TpvGraph tpvGraph) {
        // TODO Auto-generated method stub
    }

    public boolean existsYamlFile(String fileName) {
        Resource resource = appContext.getResource(YAML_FILES_ROOT + fileName);
        return resource.exists();
    }

    public void seedDatabaseVarious() {
        Provider provider = new Provider("Anonimo", "", 0, 0, "", "");
        providerDao.save(provider);
        for (int i = 1; i < 10000; i++) {
            Article article = new Article(String.valueOf(i), "Varios (" + i / 100 + "," + i % 100 + ")", new BigDecimal(i).movePointLeft(2),
                    "Varios, sin cod. barras", new BigDecimal(i).movePointLeft(2), provider);
            article.setStock(100000);
            articleDao.save(article);
        }
    }

    public void deleteAllExceptAdmin() {
        invoiceDao.deleteAll();
        ticketDao.deleteAll();

        authorizationDao.deleteAll();
        tokenDao.deleteAll();
        userDao.deleteAll();

        voucherDao.deleteAll();
        for (CategoryComponent component : categoryComponentDao.findAll()) {
            if (component.isCategory()) {
                categoryComponentDao.delete(component);
            }
        }
        categoryComponentDao.deleteAll();

        articleDao.deleteAll();
        embroideryDao.deleteAll();
        textilePrintingDao.deleteAll();
        providerDao.deleteAll();

        cashierClosureDao.deleteAll();

        createDefaultAdmin();
    }

}

package services;

import static config.ResourceNames.ADMIN_FILE;
import static config.ResourceNames.DEFAULT_SEED_FILE;
import static config.ResourceNames.YAML_FILES_ROOT;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import daos.core.ArticleDao;
import daos.core.CashierClosureDao;
import daos.core.CategoryComponentDao;
import daos.core.EmbroideryDao;
import daos.core.InvoiceDao;
import daos.core.ProviderDao;
import daos.core.TextilePrintingDao;
import daos.core.TicketDao;
import daos.core.VoucherDao;
import daos.users.AuthorizationDao;
import daos.users.TokenDao;
import daos.users.UserDao;
import entities.core.CategoryComponent;
import entities.core.CategoryComposite;
import entities.core.ProductCategory;
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
    private CashierClosureDao cashierClosureDao;

    @PostConstruct
    public void createDefaultAdmin() {
        Yaml adminYaml = new Yaml();
        Resource resource = appContext.getResource(YAML_FILES_ROOT + ADMIN_FILE);
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
            System.err.println("ERROR: File " + ADMIN_FILE + " doesn't exist or can't be opened");
            e.printStackTrace();
        }
    }

    public void seedDatabase(String fileName) {
        assert fileName != null && !fileName.isEmpty();
        if (!fileName.equals(ADMIN_FILE)) {
            Constructor constructor = new Constructor(TpvGraph.class);
            Yaml yamlParser = new Yaml(constructor);
            Resource resource = appContext.getResource(YAML_FILES_ROOT + fileName);
            InputStream input;
            try {
                input = resource.getInputStream();
                TpvGraph tpvGraph = (TpvGraph) yamlParser.load(input);

                userDao.save(tpvGraph.getUserList());
                authorizationDao.save(tpvGraph.getAuthorizationList());
                tokenDao.save(tpvGraph.getTokenList());
                voucherDao.save(tpvGraph.getVoucherList());
                providerDao.save(tpvGraph.getProviderList());
                articleDao.save(tpvGraph.getArticleList());
                embroideryDao.save(tpvGraph.getEmbroideryList());
                textilePrintingDao.save(tpvGraph.getTextilePrintingList());
                categoryComponentDao.save(buildCategoryComponentList());
                ticketDao.save(tpvGraph.getTicketList());
                invoiceDao.save(tpvGraph.getInvoiceList());
                cashierClosureDao.save(tpvGraph.getCashierClosureList());
            } catch (IOException e) {
                System.err.println("ERROR: File " + fileName + " doesn't exist or can't be opened");
                e.printStackTrace();
            }
        }
    }

    private List<CategoryComponent> buildCategoryComponentList() {
        List<CategoryComponent> categoryComponents = new ArrayList<>();
        CategoryComposite categoryCompositeRoot = new CategoryComposite(null, "category_root");
        CategoryComposite embroideriesCategoryComposite = new CategoryComposite(null, "Embroideries");
        CategoryComposite articlesCategoryComposite = new CategoryComposite(null, "Articles");
        CategoryComposite textilePrintingsCategoryComposite = new CategoryComposite(null, "TextilePrintings");
        CategoryComposite textilePrintingsCategoryComposite1 = new CategoryComposite(null, "TextilePrintings1");
        CategoryComposite textilePrintingsCategoryComposite2 = new CategoryComposite(null, "TextilePrintings2");
        CategoryComposite textilePrintingsCategoryComposite3 = new CategoryComposite(null, "TextilePrintings3");
        ProductCategory textilePrinting7400000003333 = new ProductCategory(textilePrintingDao.findAll().get(0));
        ProductCategory article7400000001111 = new ProductCategory(articleDao.findAll().get(0));
        ProductCategory embroidery7400000002222 = new ProductCategory(embroideryDao.findAll().get(0));
        textilePrintingsCategoryComposite1 = categoryComponentDao.save(textilePrintingsCategoryComposite1);
        textilePrintingsCategoryComposite2 = categoryComponentDao.save(textilePrintingsCategoryComposite2);
        textilePrintingsCategoryComposite3 = categoryComponentDao.save(textilePrintingsCategoryComposite3);
        textilePrinting7400000003333 = categoryComponentDao.save(textilePrinting7400000003333);
        article7400000001111 = categoryComponentDao.save(article7400000001111);
        embroidery7400000002222 = categoryComponentDao.save(embroidery7400000002222);
        embroideriesCategoryComposite.addCategoryComponent(embroidery7400000002222);
        articlesCategoryComposite.addCategoryComponent(article7400000001111);
        textilePrintingsCategoryComposite.addCategoryComponent(textilePrinting7400000003333);
        embroideriesCategoryComposite = categoryComponentDao.save(embroideriesCategoryComposite);
        articlesCategoryComposite = categoryComponentDao.save(articlesCategoryComposite);
        textilePrintingsCategoryComposite = categoryComponentDao.save(textilePrintingsCategoryComposite);
        categoryCompositeRoot.addCategoryComponent(embroideriesCategoryComposite);
        categoryCompositeRoot.addCategoryComponent(articlesCategoryComposite);
        categoryCompositeRoot.addCategoryComponent(textilePrintingsCategoryComposite);
        categoryCompositeRoot.addCategoryComponent(textilePrintingsCategoryComposite1);
        categoryCompositeRoot.addCategoryComponent(textilePrintingsCategoryComposite2);
        categoryCompositeRoot.addCategoryComponent(textilePrintingsCategoryComposite3);
        categoryComponents.add(categoryCompositeRoot);
        return categoryComponents;
    }

    public boolean existsYamlFile(String fileName) {
        Resource resource = appContext.getResource(YAML_FILES_ROOT + fileName);
        return resource.exists();
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

    public void seed() {
        seedDatabase(DEFAULT_SEED_FILE);
    }
}

package daos;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import entities.core.Article;
import entities.core.Embroidery;
import entities.core.Invoice;
import entities.core.Product;
import entities.core.Provider;
import entities.core.Shopping;
import entities.core.TextilePrinting;
import entities.core.Ticket;
import entities.core.TicketState;
import entities.core.Voucher;
import entities.users.Authorization;
import entities.users.Role;
import entities.users.Token;
import entities.users.User;
import services.DatabaseSeederService;

@Service
public class DaosServiceIntegrationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private DatabaseSeederService dataService;

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

    @PostConstruct
    public void populate() {
        this.createUsers(0, 4, Role.CUSTOMER);
        this.createToken(666000000);
        this.createVouchers();
        this.createProviders();
        this.createProducts();
        this.createTickets();
        this.createInvoices();
    }

    public void createUsers(int initial, int size, Role role) {
        User user;
        for (int i = 0; i < size; i++) {
            user = new User(666000000 + initial + i, role.toString().toLowerCase() + (i + initial), "pass");
            userDao.save(user);
            authorizationDao.save(new Authorization(user, role));
        }
    }

    public Token createToken(long mobile) {
        Token token;
        User user = userDao.findByMobile(mobile);
        token = new Token(user);
        tokenDao.save(token);
        return token;
    }

    public void createVouchers() {
        Voucher voucher;
        for (int i = 0; i < 4; i++) {
            voucher = new Voucher(new BigDecimal(i));
            voucherDao.save(voucher);
        }
    }

    public void createProviders() {
        Provider provider;
        for (int i = 0; i < 4; i++) {
            provider = new Provider("company" + i, "address" + i, 666100000 + i, 916661000 + i, "No", "No");
            providerDao.save(provider);
        }
    }

    public void createProducts() {
        Article article;
        Provider provider = providerDao.findAll().get(0);
        for (int i = 0; i < 4; i++) {
            article = new Article(84000001111L + i, "article" + i, new BigDecimal(20 + i), "article" + i, new BigDecimal(10 + i), provider);
            articleDao.save(article);
        }
        provider = providerDao.findAll().get(1);
        for (int i = 5; i < 9; i++) {
            article = new Article(84000001111L + i, "article" + i, new BigDecimal(20 + i), "article" + i, new BigDecimal(10 + i), provider);
            articleDao.save(article);
        }
        Embroidery embroidery;
        for (int i = 0; i < 4; i++) {
            embroidery = new Embroidery(84000002222L + i, "embroidery" + i, new BigDecimal(20 + i), "embroidery" + i, i * 1000, i, i * 10);
            embroideryDao.save(embroidery);
        }
        TextilePrinting textilePrinting;
        for (int i = 0; i < 4; i++) {
            textilePrinting = new TextilePrinting(84000003333L + i, "textilePrinting" + i, new BigDecimal(20 + i), "textilePrinting" + i,
                    "ploter");
            textilePrintingDao.save(textilePrinting);
        }

    }

    public void createTickets() {
        Ticket ticket;

        ticket = new Ticket(1L, TicketState.CLOSED);
        for (int i = 0; i < 4; i++) {
            Product product = articleDao.findOne(84000001111L + i);
            ticket.addShopping(new Shopping(1 + i, 0, product.getId(), product.getDescription(), product.getRetailPrice()));
        }
        ticketDao.save(ticket);

        ticket = new Ticket(2L, TicketState.OPENED);
        for (int i = 0; i < 4; i++) {
            Product product = embroideryDao.findOne(84000002222L + i);
            ticket.addShopping(new Shopping(1 + i, 0, product.getId(), product.getDescription(), product.getRetailPrice()));
        }
        ticketDao.save(ticket);

        ticket = new Ticket(3L, TicketState.OPENED);
        for (int i = 0; i < 4; i++) {
            Product product = textilePrintingDao.findOne(84000003333L + i);
            ticket.addShopping(new Shopping(1 + i, 10, product.getId(), product.getDescription(), product.getRetailPrice()));
        }
        ticketDao.save(ticket);
    }

    public void createInvoices() {
        invoiceDao.save(new Invoice(1, ticketDao.findOne(1L)));
        invoiceDao.save(new Invoice(3, ticketDao.findOne(3L)));
    }

    public void deleteAll() {
        dataService.deleteAllExceptAdmin();
    }
}

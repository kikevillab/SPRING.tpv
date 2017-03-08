package services;

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

@Service
public class DataService {

    @Autowired
    private DatabaseSedeer populate;

    @Autowired
    private AuthorizationDao authorizationDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

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

    public void deleteAllExceptAdmin() {
        invoiceDao.deleteAll();
        ticketDao.deleteAll();

        authorizationDao.deleteAll();
        tokenDao.deleteAll();
        userDao.deleteAll();

        voucherDao.deleteAll();

        articleDao.deleteAll();
        embroideryDao.deleteAll();
        textilePrintingDao.deleteAll();
        providerDao.deleteAll();

        populate.createDefaultAdmin();
    }

}

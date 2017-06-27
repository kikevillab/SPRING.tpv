package api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleResourceFunctionalTesting.class,
    EmbroideryResourceFunctionalTesting.class,
    TextilePrintingResourceFunctionalTesting.class,
    TokenResourceFunctionalTesting.class,
    UserResourceFunctionalTesting.class,
    VoucherResourceFunctionalTesting.class,
    //DatabaseSeedResourceFunctionalTesting.class,
    //ProductResourceFunctionalTesting.class, 
    //PdfGenerationResourceFunctionalTesting.class,
    //TicketResourceFunctionalTesting.class,
    //InvoiceResourceFunctionalTesting.class,
    //CategoryResourceFunctionalTesting.class
})

public class AllFunctionalsTesting {

}

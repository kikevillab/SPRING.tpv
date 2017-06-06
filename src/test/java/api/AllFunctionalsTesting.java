package api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({UserResourceFunctionalTesting.class, TokenResourceFunctionalTesting.class, DatabaseSeedResourceFunctionalTesting.class,
        ProductResourceFunctionalTesting.class, PdfGenerationResourceFunctionalTesting.class, TicketResourceFunctionalTesting.class,
        InvoiceResourceFunctionalTesting.class, EmbroideryResourceFunctionalTesting.class, TextilePrintingResourceFunctionalTesting.class,
        ArticleResourceFunctionalTesting.class, VoucherResourceFunctionalTesting.class})

public class AllFunctionalsTesting {

}

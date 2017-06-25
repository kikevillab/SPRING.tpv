package controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    ArticleControllerIT.class, 
    CashierClosureControllerIT.class,
    CategoryControllerIT.class,
    EmbroideryControllerIT.class, 
    InvoiceControllerIT.class, 
    ProductControllerIT.class, 
    TextilPrintingControllerIT.class,
    TicketControllerIT.class,
    TokenControllerIT.class, 
    UserControllerIT.class,
    VoucherControllerIT.class
})
public class AllControllersIntegrationTests {
}

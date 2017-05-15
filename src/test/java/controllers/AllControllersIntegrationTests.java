package controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TokenControllerIT.class, ProductControllerIT.class, InvoiceControllerIT.class, UserControllerIT.class,
        ArticleControllerIT.class, EmbroideryControllerIT.class, TextilPrintingControllerIT.class})
public class AllControllersIntegrationTests {
}

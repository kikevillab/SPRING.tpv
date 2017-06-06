package services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DatabaseSeederServiceIT.class, PdfGeneratorServiceIT.class, ServiceBatchTesting.class})
public class AllServicesIntegrationTests {
    
}

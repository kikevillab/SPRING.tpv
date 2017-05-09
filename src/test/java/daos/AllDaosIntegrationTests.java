package daos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import daos.core.AllDaosCoreIntegrationTests;
import daos.users.AllDaosUsersIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({AllDaosCoreIntegrationTests.class, AllDaosUsersIntegrationTests.class})
public class AllDaosIntegrationTests {

}

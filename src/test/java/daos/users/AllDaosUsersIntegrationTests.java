package daos.users;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TokenDaoIT.class, UserDaoIT.class, AuthorizationDaoIT.class})
public class AllDaosUsersIntegrationTests {

}

package entities;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import entities.core.AllEntitiesCoreTests;
import entities.users.AllEntitiesUsersTests;

@RunWith(Suite.class)
@SuiteClasses({AllEntitiesUsersTests.class, AllEntitiesCoreTests.class})

public class AllEntitiesTests {

}

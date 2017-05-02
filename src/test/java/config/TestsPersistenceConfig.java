package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {ResourceNames.DAOS, ResourceNames.DAOS_USERS, ResourceNames.DAOS_CORE, ResourceNames.SERVICES})
public class TestsPersistenceConfig {

}

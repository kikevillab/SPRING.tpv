package config;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(ResourceNames.PROPERTIES)
@EnableJpaRepositories(basePackages = {ResourceNames.DAOS_USERS, ResourceNames.DAOS_CORE}, repositoryImplementationPostfix = "Impl")
@EnableTransactionManagement
public class PersistenceConfig {

    @Autowired
    private Environment environment;
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(environment.getProperty("dataSource.url"));
        dataSource.setUsername(environment.getProperty("dataSource.username"));
        dataSource.setPassword(environment.getProperty("dataSource.password"));
        LogManager.getLogger(this.getClass()).info("Datasource: " + dataSource.getUrl());
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        Properties properties = new Properties();
        properties.put("hibernate.connection.charSet", "UTF-8");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.format_sql", "true");
        // create-drop, create, update, validate
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl"));
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        entityManagerFactoryBean.setJpaProperties(properties);
        entityManagerFactoryBean.setPackagesToScan(ResourceNames.ENTITIES_USERS, ResourceNames.ENTITIES_CORE);
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }
    
    @PostConstruct
    protected void initialize() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        //Crea las tablas de metadatos de Spring Batch
        //VER: https://www.mkyong.com/spring-batch/spring-batch-metadata-tables-are-not-created-automatically/
        populator.addScript(resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-drop-mysql.sql"));
        populator.addScript(resourceLoader.getResource("classpath:/org/springframework/batch/core/schema-mysql.sql"));
        populator.setContinueOnError(true);
        DatabasePopulatorUtils.execute(populator, dataSource());
    }

}

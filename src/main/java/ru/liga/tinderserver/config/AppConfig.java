package ru.liga.tinderserver.config;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/league_db");

        //TODO: System.getenv вынести в переменные среды
        ds.setUsername("admin");
        ds.setPassword("qwerty$4");
        return ds;
    }

    @Bean
    public SpringLiquibase springLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:/db/changelog-master.yaml");
        return liquibase;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new
                LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("ru.liga.tinderserver.entity");
        em.setPersistenceUnitName("dbtinder");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties());
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }

    @Bean
    public Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }

}

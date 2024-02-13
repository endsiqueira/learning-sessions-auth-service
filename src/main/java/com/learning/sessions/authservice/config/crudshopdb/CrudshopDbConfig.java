package com.learning.sessions.authservice.config.crudshopdb;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.learning.sessions.authservice.repository.crudshop",
        entityManagerFactoryRef = "crudshopEntityManagerFactory",
        transactionManagerRef = "crudshopTransactionManager"
)
@EntityScan("com.learning.sessions.authservice.model.crudshop")
@PropertySource("classpath:application.properties")
public class CrudshopDbConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Bean(name = "crudshopDataSource")
    @ConfigurationProperties(prefix = "crudshop.datasource")
    public DataSource crudshopDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "crudshopEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean crudshopEntityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource crudshopDataSource) {
        return builder
                .dataSource(crudshopDataSource)
                .packages("com.learning.sessions.authservice.model.crudshop")
                .persistenceUnit("crudshop")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Bean(name = "crudshopTransactionManager")
    public PlatformTransactionManager crudshopTransactionManager(
            LocalContainerEntityManagerFactoryBean crudshopEntityManagerFactory) {
        return new JpaTransactionManager(crudshopEntityManagerFactory.getObject());
    }
}

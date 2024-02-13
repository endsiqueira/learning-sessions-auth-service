package com.learning.sessions.authservice.config.authdb;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.learning.sessions.authservice.repository.auth",
        entityManagerFactoryRef = "authEntityManagerFactory",
        transactionManagerRef = "authTransactionManager"
)
@EntityScan("com.learning.sessions.authservice.model.auth")
@PropertySource("classpath:application.properties")
public class AuthDbConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Primary
    @Bean(name = "authDataSource")
    @ConfigurationProperties(prefix = "auth.datasource")
    public DataSource authDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "authEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean authEntityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource authDataSource) {
        return builder
                .dataSource(authDataSource)
                .packages("com.learning.sessions.authservice.model.auth")
                .persistenceUnit("auth")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Primary
    @Bean(name = "authTransactionManager")
    public PlatformTransactionManager authTransactionManager(
            LocalContainerEntityManagerFactoryBean authEntityManagerFactory) {
        return new JpaTransactionManager(authEntityManagerFactory.getObject());
    }
}

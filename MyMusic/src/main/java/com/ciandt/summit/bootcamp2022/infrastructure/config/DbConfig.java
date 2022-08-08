package com.ciandt.summit.bootcamp2022.infrastructure.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration
public class DbConfig {

    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:MyMusic.db");
        dataSource.setUsername(env.getProperty(""));
        dataSource.setPassword(env.getProperty(""));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.ciandt.summit.bootcamp2022.infrastructure.adapters.repositories.entities");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        if (env.getProperty(HIBERNATE_HBM2DDL_AUTO) != null) {
            hibernateProperties.setProperty(HIBERNATE_HBM2DDL_AUTO, env.getProperty(HIBERNATE_HBM2DDL_AUTO));
        }
        if (env.getProperty(HIBERNATE_DIALECT) != null) {
            hibernateProperties.setProperty(HIBERNATE_DIALECT, env.getProperty(HIBERNATE_DIALECT));
        }
        if (env.getProperty(HIBERNATE_SHOW_SQL) != null) {
            hibernateProperties.setProperty(HIBERNATE_SHOW_SQL, env.getProperty(HIBERNATE_SHOW_SQL));
        }
        return hibernateProperties;
    }

}

@Configuration
class SqliteConfig {
}

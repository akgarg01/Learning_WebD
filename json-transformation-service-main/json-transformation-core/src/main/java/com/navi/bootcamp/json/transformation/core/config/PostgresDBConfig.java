package com.navi.bootcamp.json.transformation.core.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.navi.bootcamp.json.transformation.core.repository",
    entityManagerFactoryRef = "postgresEntityManager",
    transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDBConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.configuration")
    public DataSource dataSource() {
        return postgresDataSourceProperties().initializeDataSourceBuilder()
            .type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean postgresEntityManager(
        EntityManagerFactoryBuilder builder,
        @Qualifier("dataSource") DataSource dataSource,
        ConfigurableListableBeanFactory beanFactory) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder
            .dataSource(dataSource)
            .packages("com.navi.bootcamp.json.transformation.core.entity")
            .build();

        entityManagerFactoryBean.getJpaPropertyMap().put(AvailableSettings.BEAN_CONTAINER,
            new SpringBeanContainer(beanFactory));
        entityManagerFactoryBean.getJpaPropertyMap()
            .put(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        entityManagerFactoryBean.getJpaPropertyMap()
            .put(AvailableSettings.IMPLICIT_NAMING_STRATEGY,
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");

        return entityManagerFactoryBean;
    }

    @Primary
    @Bean
    public PlatformTransactionManager postgresTransactionManager(
        @Qualifier("postgresEntityManager") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory) {
        return new JpaTransactionManager(postgresEntityManagerFactory.getObject());
    }
}

package simonov.hotel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:hibernate.properties")
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setPackagesToScan("simonov.hotel.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    @Bean
    public DataSource dataSource() {
        String dbUrl = env.getProperty("db.url");
        String dbUser = env.getProperty("db.username");
        String dbPass = env.getProperty("db.password");
        DriverManagerDataSource dataSource = new DriverManagerDataSource(dbUrl, dbUser, dbPass);
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
                setProperty("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
                setProperty("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
                setProperty("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
                setProperty("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));

                setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
            }
        };
    }
}

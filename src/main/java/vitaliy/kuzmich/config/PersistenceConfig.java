package vitaliy.kuzmich.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import vitaliy.kuzmich.model.PositionModel;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
//@PropertySource({"classpath:hibernate.properties", "classpath:hibernate.properties"})

public class PersistenceConfig {

    /*  @Autowired
      private Environment env;*/
    @Autowired
    ServletContext context;

    @Bean
    public SessionFactory sessionFactory() {
        Properties props = hibernateProperties();
        LocalSessionFactoryBuilder builder =
                new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("vitaliy")
                .addProperties(props);

        java.lang.String[] s = builder.generateSchemaCreationScript(Dialect.getDialect(props));
        String regEx = "create table `" + PositionModel.class.getSimpleName() + "`";
        for (String createTable : s) {
            if (createTable.startsWith(regEx)) {
                Const.SQL_CREATE_POSITION_MODEL = createTable.replaceFirst(PositionModel.class.getSimpleName(), Const.KEY_TABLE_NAME);
                break;
            }
        }

        return builder.buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        final Properties pr = new Properties();
        InputStream ins = null;
        try {
            ins = context.getResourceAsStream(Const.PATH_HIKARI_PROPS);
            pr.load(ins);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HikariConfig hconf = new HikariConfig(pr);
        HikariDataSource res = new HikariDataSource(hconf);

        return res;

    }


    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties hibernateProperties() {
        final Properties pr = new Properties();
        InputStream ins = null;
        try {
            ins = context.getResourceAsStream(Const.PATH_HIBERNATE_PROPS);
            pr.load(ins);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pr;
    }

}
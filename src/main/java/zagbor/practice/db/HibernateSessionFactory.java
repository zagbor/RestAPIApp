package zagbor.practice.db;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import zagbor.practice.model.Event;
import zagbor.practice.model.File;
import zagbor.practice.model.User;

import java.util.Properties;


public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = HibernateSessionFactory.buildSessionFactory(configurationFactory());
        }
        return sessionFactory;
    }


    private static SessionFactory buildSessionFactory(Configuration configuration) {
        serviceRegistryFactory();
        return configuration.buildSessionFactory();
    }

    private static StandardServiceRegistry serviceRegistryFactory() {
        return new StandardServiceRegistryBuilder()
                .applySettings(configurationFactory().getProperties()).build();
    }

    private static Configuration configurationFactory() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.URL, "jdbc:postgresql://ec2-54-162-119-125.compute-1.amazonaws.com:5432/d7qsecv49kh6is");
        settings.put(Environment.USER, "ugjlvryoihjiuq");
        settings.put(Environment.PASS, "3b07331420038a45b413da05fbb166b17df29f3a9d2d72820dc5130913bf20");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(File.class);
        configuration.addAnnotatedClass(User.class);

        return configuration;
    }

}

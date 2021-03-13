package zagbor.practice.db;


import org.hibernate.SessionFactory;
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
            sessionFactory = HibernateSessionFactory.buildSessionFactory();
        }
        return sessionFactory;
    }


    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/restappdb");
        settings.put(Environment.USER, "postgres");
        settings.put(Environment.PASS, "root");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(File.class);
        configuration.addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();
    }
}

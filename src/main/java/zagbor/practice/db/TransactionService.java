package zagbor.practice.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionService {

    private static SessionFactory sessionFactory =HibernateSessionFactory.getSessionFactory();

    public static void doInTransactionVoid(Consumer<Session> consumer) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        consumer.accept(session);
        session.getTransaction().commit();
        session.close();
    }


    public static  <R> R doInTransactionFunc(Function<Session, R> function) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        R r = function.apply(session);
        session.getTransaction().commit();
        session.close();
        return r;
    }

}

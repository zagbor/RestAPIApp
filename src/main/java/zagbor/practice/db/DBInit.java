package zagbor.practice.db;

public class DBInit {
    public static void init() {
        DBController.getDataUser();
        HibernateSessionFactory.getSessionFactory();
        FlywayInit.init();
    }


}

package zagbor.practice.db;

import org.junit.Test;

public class DBInit {
    public static void init() {
        DBController.getDataUser();
        HibernateSessionFactory.getSessionFactory();
        FlywayInit.init();


    }

    @Test
    public void initTest() {
        init();
    }


}

package zagbor.practice.db;

import org.flywaydb.core.Flyway;
import org.junit.Test;

public class FlywayInit {
    private static Flyway flyway;

    public static void init() {
        flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/restappdb",
                "postgres",
                "root").load();
        flyway.clean();
        flyway.migrate();
    }

@Test
    public void testInit(){
        init();
}
}
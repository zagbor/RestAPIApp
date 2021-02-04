package zagbor.practice.db;

import org.flywaydb.core.Flyway;

public class FlywayInit {
    private static Flyway flyway;


    public static void init() {
        flyway = Flyway.configure().dataSource(DatabaseConnectionsProperties.databaseConnectionsProperties.getUrl(),
                DatabaseConnectionsProperties.databaseConnectionsProperties.getUsername(),
                DatabaseConnectionsProperties.databaseConnectionsProperties.getPassword()).load();
        flyway.clean();
        flyway.migrate();
    }



}
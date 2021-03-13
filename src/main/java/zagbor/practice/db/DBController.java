package zagbor.practice.db;

public class DBController {
    public static DatabaseConnectionsProperties getDataUser() {

        return DatabaseConnectionsProperties.databaseConnectionsProperties("jdbc:postgresql://localhost:5432/restappdb", "postgres", "root");
    }


}

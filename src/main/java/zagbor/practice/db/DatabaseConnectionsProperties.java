package zagbor.practice.db;

import lombok.Data;

@Data

public class DatabaseConnectionsProperties {
    public static DatabaseConnectionsProperties databaseConnectionsProperties;
    private String url;
    private String username;
    private String password;

    private DatabaseConnectionsProperties() {
    }

    public static DatabaseConnectionsProperties databaseConnectionsProperties(String url, String username, String password) {
        if (databaseConnectionsProperties == null) {
            databaseConnectionsProperties = init(url, username, password);
        }
        return databaseConnectionsProperties;
    }

    private static DatabaseConnectionsProperties init(String url, String username, String password) {
        databaseConnectionsProperties = new DatabaseConnectionsProperties();
        databaseConnectionsProperties.url = url;
        databaseConnectionsProperties.username = username;
        databaseConnectionsProperties.password = password;
        return databaseConnectionsProperties;
    }

}

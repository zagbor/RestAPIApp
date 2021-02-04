package zagbor.practice.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DBController {

    private final static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static DatabaseConnectionsProperties getDataUser() {
        System.out.println("Введите данные для поключения.");
        System.out.println("Введите URL вашей базы:");
        String url = null;
        String username = null;
        String password = null;
        try {
            url = reader.readLine();
            System.out.println("Введите USERNAME вашей базы:");
            username = reader.readLine();
            System.out.println("Введите PASSWORD вашей базы:");
            password = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DatabaseConnectionsProperties.databaseConnectionsProperties(url, username, password);
    }


}

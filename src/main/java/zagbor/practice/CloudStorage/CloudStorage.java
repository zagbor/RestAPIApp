package zagbor.practice.CloudStorage;

import java.io.IOException;
import java.io.InputStream;

public interface CloudStorage {
    void putFile(String name, InputStream input);

    void deleteFile(String name);

    InputStream getUrl(String name) throws IOException;

    void changeName(String oldName, String newName);

}

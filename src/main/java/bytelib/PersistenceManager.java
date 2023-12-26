package bytelib;

import java.io.Serializable;

interface PersistenceManager<T extends Serializable> {
    void save(T data, String filePath);
    T load(String filePath);
}
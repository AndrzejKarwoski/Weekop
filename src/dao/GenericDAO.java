package dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO <T, PK extends Serializable> {
    // CRUD Methods (CREATE READ UPDATE DELETE)

    T create(T object);
    T read(PK key);
    boolean update(T object);
    boolean delete(PK key);
    List<T> getAll();
}

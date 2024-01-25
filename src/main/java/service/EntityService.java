package service;

import java.io.Serializable;
import java.util.List;

/**
 * @param <E> the type of entity
 * @param <ID> the type of id field
 * */
public interface EntityService<E, ID extends Serializable> {
    E create(E e);
    E getById(ID id);
    List<E> getAll();
    boolean update(E e);
    boolean deleteById(ID id);
}

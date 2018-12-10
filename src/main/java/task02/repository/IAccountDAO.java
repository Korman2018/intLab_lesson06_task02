package task02.repository;

import java.util.List;

public interface IAccountDAO<T> {
    List<T> getAll();

    void add(T object);

    void removeAll();
}

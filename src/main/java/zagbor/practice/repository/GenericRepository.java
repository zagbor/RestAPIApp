package zagbor.practice.repository;


import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> getById(ID id);

    List<T> getAll() ;

    T update(T t);

    T create(T t);

    void deleteById(ID id);


}

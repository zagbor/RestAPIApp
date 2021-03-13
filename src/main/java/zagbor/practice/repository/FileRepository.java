package zagbor.practice.repository;

import zagbor.practice.model.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends GenericRepository<File, Long> {
    List<File> getAll(long id);

    Optional<File> getById(long id, long userId);
}

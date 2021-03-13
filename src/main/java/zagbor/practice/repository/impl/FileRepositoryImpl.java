package zagbor.practice.repository.impl;


import org.hibernate.query.Query;
import zagbor.practice.model.File;
import zagbor.practice.model.User;
import zagbor.practice.repository.FileRepository;

import java.util.List;
import java.util.Optional;

import static zagbor.practice.db.TransactionService.doInTransactionFunc;
import static zagbor.practice.db.TransactionService.doInTransactionVoid;

public class FileRepositoryImpl implements FileRepository {

    @Override
    public Optional<File> getById(Long id) {
        return doInTransactionFunc(session -> {
            return Optional.ofNullable(session.get(File.class, id))
                    .map(file -> {
                        User user = User.builder()
                                .id(file.getUser().getId())
                                .name(file.getUser().getName())
                                .build();

                        return File.builder()
                                .id(file.getId())
                                .name(file.getName())
                                .fileStatus(file.getFileStatus())
                                .user(user)
                                .build();
                    });
        });
    }

    @Override
    public List<File> getAll() {
        return doInTransactionFunc(session -> {
            return session.createQuery("FROM File", File.class).list();
        });
    }

    @Override
    public List<File> getAll(long userId) {
        return doInTransactionFunc(session -> {
            String hql = "FROM File f WHERE f.user.id = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            return query.list();
        });
    }

    @Override
    public Optional<File> getById(long id, long userId) {
        return doInTransactionFunc(session -> {
            String hql = "FROM File f WHERE f.user.id = :userId AND f.id= :id";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("id", id);
            return Optional.ofNullable((File) query.getSingleResult());
        });
    }

    @Override
    public File update(File file) {
        return doInTransactionFunc(session -> {
            session.update(file);
            return file;
        });
    }

    @Override
    public File create(File file) {
        return doInTransactionFunc(session -> {
            session.save(file);
            return file;
        });
    }

    @Override
    public void deleteById(Long id) {
        doInTransactionVoid(session -> {
            File file = new File();
            file.setId(id);
            session.delete(file);
        });
    }
}

package zagbor.practice.repository.impl;

import org.junit.Test;
import zagbor.practice.model.User;
import zagbor.practice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static zagbor.practice.db.TransactionService.doInTransactionFunc;
import static zagbor.practice.db.TransactionService.doInTransactionVoid;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> getById(Long id) {
        return doInTransactionFunc(session -> {
            return Optional.ofNullable(session.get(User.class, id))
                    .map(user -> {
                        return User.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .files(user.getFiles())
                                .role(user.getRole())
                                .build();
                    });
        });
    }


    @Override
    public List<User> getAll() {
        return doInTransactionFunc(session -> {
            List<User> users =
                    session.createQuery(
                            "FROM User", User.class).list();
            return users;

        });
    }

    @Override
    public User update(User user) {
        return doInTransactionFunc(session -> {
            session.update(user);
            return user;
        });
    }

    @Override
    public User create(User user) {
        return doInTransactionFunc(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    @Override
    public void deleteById(Long id) {
        doInTransactionVoid(session -> {
            User user = User.builder()
                    .id(id)
                    .build();
            session.delete(user);
        });
    }

    @Test
    public void testGetAll() {
        System.out.println(getAll());
    }


}

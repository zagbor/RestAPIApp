package zagbor.practice.repository.impl;

import org.junit.Test;
import zagbor.practice.model.User;
import zagbor.practice.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static zagbor.practice.db.TransactionService.doInTransactionFunc;
import static zagbor.practice.db.TransactionService.doInTransactionVoid;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return doInTransactionFunc(session -> {
            List<User> customers = session.createQuery("FROM User", User.class).list();
            return customers;
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
            session.save(user);
            return user;
        });
    }
    @Override
    public void deleteById(Long id) {
        doInTransactionVoid(session -> {
            User user = new User();
            user.setId(id);
            session.delete(user);
        });
    }

    @Test
     public void testGetAll(){
        System.out.println(getAll());
    }
}

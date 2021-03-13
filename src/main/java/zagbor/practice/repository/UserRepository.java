package zagbor.practice.repository;

import zagbor.practice.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<User, Long> {


    List<User> getAll();
}

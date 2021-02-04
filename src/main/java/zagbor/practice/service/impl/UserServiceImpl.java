package zagbor.practice.service.impl;

import lombok.RequiredArgsConstructor;
import zagbor.practice.model.User;
import zagbor.practice.repository.UserRepository;
import zagbor.practice.repository.impl.UserRepositoryImpl;
import zagbor.practice.service.UserService;

import java.util.List;
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}

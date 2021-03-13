package zagbor.practice.controller;

import zagbor.practice.dto.UserDto;
import zagbor.practice.model.User;
import zagbor.practice.service.UserService;
import zagbor.practice.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

public class UserController {

    UserService userService = new UserServiceImpl();

    public List<UserDto> getAll() {
        return userService.getAll();
    }

    public Optional<UserDto> getById(long id) {
        return userService.getById(id);
    }

    public UserDto create(User user) {
        return userService.create(user);
    }

    public void delete(long id) {
        userService.delete(id);
    }

    public UserDto update(User user) {
        return userService.update(user);
    }
}

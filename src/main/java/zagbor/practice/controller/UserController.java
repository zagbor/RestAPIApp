package zagbor.practice.controller;

import zagbor.practice.model.User;
import zagbor.practice.service.UserService;
import zagbor.practice.service.impl.UserServiceImpl;

import java.util.List;

public class UserController {

    UserService userService = new UserServiceImpl();

    public List<User> getAll() {
        return userService.getAll();
    }
}

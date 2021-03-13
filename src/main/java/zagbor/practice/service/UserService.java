package zagbor.practice.service;

import zagbor.practice.dto.UserDto;
import zagbor.practice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAll();

    Optional<UserDto> getById(long id);

    UserDto create(User user);

    void delete(long id);

    UserDto update(User user);

}

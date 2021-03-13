package zagbor.practice.service.impl;

import lombok.RequiredArgsConstructor;
import zagbor.practice.dto.FileDto;
import zagbor.practice.dto.UserDto;
import zagbor.practice.model.User;
import zagbor.practice.repository.UserRepository;
import zagbor.practice.repository.impl.UserRepositoryImpl;
import zagbor.practice.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.getAll();
        return buildUsersDto(users);
    }

    @Override
    public Optional<UserDto> getById(long id) {
        User user = userRepository.getById(id).orElseThrow();
        return Optional.of(buildUserDto(user));
    }

    @Override
    public UserDto create(User user) {
        User newUser = userRepository.create(user);
        return buildUserDto(newUser);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public UserDto update(User user) {
        User newUser = userRepository.update(user);
        return buildUserDto(newUser);

    }

    private List<UserDto> buildUsersDto(List<User> users) {
        return users.stream().map(this::buildUserDto).collect(Collectors.toList());

    }


    private UserDto buildUserDto(User user) {
        List<FileDto> filesDto = new ArrayList<>();
        if (user.getFiles() != null) {
            filesDto = user.getFiles().stream().map(file ->
                    FileDto.builder()
                            .id(file.getId())
                            .name(file.getName())
                            .fileStatus(file.getFileStatus())
                            .build()).collect(Collectors.toList());
        }
        return UserDto.builder()
                .files(filesDto)
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }


}

package zagbor.practice.service.impl;

import zagbor.practice.CloudStorage.CloudStorage;
import zagbor.practice.CloudStorage.impl.CloudStorageAmazonS3Manager;
import zagbor.practice.dto.EventDto;
import zagbor.practice.dto.FileDto;
import zagbor.practice.dto.UserDto;
import zagbor.practice.model.Event;
import zagbor.practice.model.EventType;
import zagbor.practice.model.File;
import zagbor.practice.model.FileStatuses;
import zagbor.practice.model.User;
import zagbor.practice.repository.FileRepository;
import zagbor.practice.repository.UserRepository;
import zagbor.practice.repository.impl.FileRepositoryImpl;
import zagbor.practice.repository.impl.UserRepositoryImpl;
import zagbor.practice.service.FileService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileServiceImpl implements FileService {
    CloudStorage cloudStorage = new CloudStorageAmazonS3Manager();
    FileRepository fileRepository = new FileRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public List<FileDto> getAll() {
        List<File> files = fileRepository.getAll();
        return buildFilesDto(files);
    }

    @Override
    public List<FileDto> getAll(long userId) {
        List<File> files = fileRepository.getAll(userId);
        return buildFilesDto(files);
    }

    @Override
    public Optional<FileDto> getById(long id, long userId) {
        File file = fileRepository.getById(id, userId).orElseThrow();
        return Optional.of(buildFileDto(file));
    }

    @Override
    public Optional<FileDto> getById(long id) {
        File file = fileRepository.getById(id).orElseThrow();
        return Optional.of(buildFileDto(file));
    }

    @Override
    public InputStream getByIdData(long fileId, long userId) throws IOException {
        File file = fileRepository.getById(fileId, userId).orElseThrow();
        return cloudStorage.getUrl(file.getName());
    }


    @Override
    public FileDto create(long userId, String fileName, InputStream in) throws Exception {
        userRepository.getById(userId).orElseThrow(Exception::new);
        User user = User
                .builder()
                .id(userId)
                .build();
        cloudStorage.putFile(fileName, in);
        Event event =
                Event.builder()
                        .type(EventType.CREATE)
                        .date(new Date())
                        .build();

        File file = File.builder()
                .name(fileName)
                .events(List.of(event))
                .user(user)
                .fileStatus(FileStatuses.ACTIVE)
                .build();
        event.setFile(file);
        return buildFileDto(fileRepository.create(file));
    }

    @Override
    public FileDto update(File file) {
        String oldName = fileRepository.getById(file.getId(), file.getId()).get().getName();
        if (!oldName.equals(file.getName())) {
            cloudStorage.changeName(oldName, file.getName());
        }
        File file1 = fileRepository.update(file);
        return buildFileDto(file1);
    }

    @Override
    public void deleteById(long fileId, long userId) throws Exception {
        User user = userRepository.getById(userId).orElseThrow(Exception::new);
        user.getFiles().stream().filter(file -> file.getId() == fileId).findAny().orElseThrow(Exception::new);
        File file = fileRepository.getById(fileId).orElseThrow();
        Event event = Event.builder()
                .date(new Date())
                .type(EventType.DELETE)
                .build();
        List<Event> events = file.getEvents();
        events.add(event);
        file.setEvents(events);
        cloudStorage.deleteFile(file.getName());
    }

    private List<FileDto> buildFilesDto(List<File> files) {
        return files.stream()
                .map(this::buildFileDto)
                .collect(Collectors.toList());

    }

    private FileDto buildFileDto(File file) {
        UserDto userDto = UserDto.builder()
                .id(file.getUser().getId())
                .build();

        List<EventDto> events = file.getEvents()
                .stream()
                .map(event -> {
                    return EventDto.builder()
                            .id(event.getId())
                            .type(event.getType())
                            .date(event.getDate())
                            .build();
                }).collect(Collectors.toList());


        return FileDto.builder()
                .id(file.getId())
                .name(file.getName())
                .user(userDto)
                .events(events)
                .fileStatus(file.getFileStatus())
                .build();

    }
}

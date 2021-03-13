package zagbor.practice.service;

import zagbor.practice.dto.FileDto;
import zagbor.practice.model.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface FileService {

    List<FileDto> getAll();

    List<FileDto> getAll(long userId);

    Optional<FileDto> getById(long id, long userId);

    Optional<FileDto> getById(long id);

    FileDto create(long userId, String fileName, InputStream inputStream) throws Exception;


    void deleteById(long fileId, long userId) throws Exception;

    InputStream getByIdData(long fileId, long userId) throws IOException;

    FileDto update(File file);
}

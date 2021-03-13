package zagbor.practice.controller;

import zagbor.practice.dto.FileDto;
import zagbor.practice.model.File;
import zagbor.practice.service.FileService;
import zagbor.practice.service.impl.FileServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class FileController {

    FileService fileService = new FileServiceImpl();

    public Optional<FileDto> getById(long id, long userId) {
        return fileService.getById(id, userId);
    }

    public List<FileDto> getAll(long userId) {
        return fileService.getAll(userId);
    }

    public FileDto createData(long userId, String fileName, InputStream inputStream) throws Exception {
        return fileService.create(userId, fileName, inputStream);
    }

    public FileDto update(File file) {
        return fileService.update(file);
    }


    public void deleteById(long fileId, long userId) throws Exception {
        fileService.deleteById(fileId, userId);
    }

    public InputStream getByIdData(long fileId, long userId) throws IOException {
        return fileService.getByIdData(fileId, userId);
    }
}

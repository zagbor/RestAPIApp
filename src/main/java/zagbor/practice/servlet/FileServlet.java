package zagbor.practice.servlet;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import zagbor.practice.controller.FileController;
import zagbor.practice.dto.FileDto;
import zagbor.practice.model.File;
import zagbor.practice.model.FileStatuses;
import zagbor.practice.model.User;
import zagbor.practice.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;

@Slf4j
@WebServlet(urlPatterns = "/files/*")
@MultipartConfig
public class FileServlet extends HttpServlet {
    FileController fileController = new FileController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = null;
        long fileId;
        final FileDto file;
        final List<FileDto> files;
        String pathInfo = req.getRequestURI();
        log.info("============ Запрос [{}] ============", pathInfo);
        String getFileByIdRegex = "\\/(files)\\/?([0-9+])?\\/?(data)?";
        Matcher matcher = Utils.getMatcher(pathInfo, getFileByIdRegex);
        try {
            if (matcher.find()) {
                if (matcher.group(1).equalsIgnoreCase("files") && matcher.group(2) == null) {
                    String userIdString = req.getHeader("user_id");
                    long userId = Long.parseLong(userIdString);
                    files = fileController.getAll(userId);
                    json = new ObjectMapper().writeValueAsString(files);

                } else if (matcher.group(1).equalsIgnoreCase("files") && Utils.isNumber(matcher.group(2)) && matcher.group(3) == null) {
                    fileId = Long.parseLong(matcher.group(2));
                    String userIdString = req.getHeader("user_id");
                    long userId = Long.parseLong(userIdString);
                    file = fileController.getById(fileId, userId).orElseThrow();
                    json = new ObjectMapper().writeValueAsString(file);

                } else if (matcher.group(1).equalsIgnoreCase("files") && Utils.isNumber(matcher.group(2)) && matcher.group(3).equals("data")) {
                    fileId = Long.parseLong(matcher.group(2));
                    String userIdString = req.getHeader("user_id");
                    long userId = Long.parseLong(userIdString);
                    InputStream in = fileController.getByIdData(fileId, userId);
                    IOUtils.copy(in, resp.getOutputStream());
                    return;
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            resp.sendError(404, "Запрос не найден");
            return;
        }
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(json);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String getFileByIdRegex = "\\/(files)\\/?([0-9+])?";
        String pathInfo = req.getRequestURI();
        Matcher matcher = Utils.getMatcher(pathInfo, getFileByIdRegex);
        if (matcher.find()) {

            if (matcher.group(1).equalsIgnoreCase("files") && Utils.isNumber(matcher.group(2))) {
                File file = preCreateFile(req, matcher);
                fileController.update(file);
            } else {
                resp.sendError(404, "Что-то пошло не так");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userIdString = req.getHeader("user_id");
        String fileIdString = req.getHeader("file_id");
        long fileId = Long.parseLong(fileIdString);
        long userId = Long.parseLong(userIdString);
        try {
            fileController.deleteById(fileId, userId);
        } catch (Exception e) {
            resp.sendError(404, "Запрос не найден");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        InputFile inputFile = preCreateInputFile(req);
        String userIdString = req.getHeader("user_id");
        inputFile.userId = Long.parseLong(userIdString);
        try {
            fileController.createData( inputFile.userId, inputFile.fileName, inputFile.io);
        } catch (Exception e) {
            resp.sendError(404, "Запрос не найден");
        }

    }

    private InputFile preCreateInputFile(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("file");
        return new InputFile() {{
            this.fileName = filePart.getSubmittedFileName();
            this.io = filePart.getInputStream();
        }};
    }


    private File preCreateFile(HttpServletRequest req, Matcher matcher) {
        String userIdString = req.getHeader("user_id");
        long fileId = Long.parseLong(matcher.group(2));
        long userId = Long.parseLong(userIdString);
        String fileStatusString = req.getParameter("file_status");
        FileStatuses status = FileStatuses.valueOf(fileStatusString);
        String filename = req.getParameter("file_name");
       return File.builder()
                .id(fileId)
                .name(filename)
                .user(User.builder()
                        .id(userId)
                        .build())
                .fileStatus(status)
                .build();
    }

}

class InputFile {
    long userId;
    String fileName;
    InputStream io;
}


package zagbor.practice.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import zagbor.practice.controller.UserController;
import zagbor.practice.dto.UserDto;
import zagbor.practice.model.User;
import zagbor.practice.model.UserRole;
import zagbor.practice.util.UserUtils;
import zagbor.practice.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@WebServlet(urlPatterns = "/users/*")
public class UserServlet extends HttpServlet {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final UserController userController = new UserController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String json = null;
        long userId;
        final UserDto user;
        final List<UserDto> users;
        String pathInfo = req.getRequestURI();
        log.info("============ Запрос [{}] ============", pathInfo);
        String regex = "\\/(users)\\/?([0-9]+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pathInfo);
        if (matcher.find()) {
            try {
                if (matcher.group(1).equals("users") && matcher.group(2) == null) {
                    users = userController.getAll();
                    json = OBJECT_MAPPER.writeValueAsString(users);
                } else if (matcher.group(1).equals("users") && Utils.isNumber(matcher.group(2))) {
                    userId = Long.parseLong(matcher.group(2));
                    user = userController.getById(userId).orElseThrow();
                    json = OBJECT_MAPPER.writeValueAsString(user);
                } else {
                    throw new Exception();
                }

            } catch (Exception e) {
                json = OBJECT_MAPPER.writeValueAsString("Запрос введен неверно.");
            } finally {
                res.setContentType("application/json; charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.write(json);
            }

        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        req.setCharacterEncoding("UTF-8");
        if (UserUtils.requestIsValid(req)) {
            final String name = req.getParameter("name");
            final String role = req.getParameter("role");
            final User user =
                    User.builder()
                            .name(name)
                            .role(UserRole.valueOf(role))
                            .build();
            userController.create(user);
        }
        resp.setStatus(201);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        req.setCharacterEncoding("UTF-8");
        if (UserUtils.requestIsValid(req)) {
            final String userIdString = req.getParameter("user_id");
            final String name = req.getParameter("name");
            final String role = req.getParameter("role");
            long userId = 0;
            if (Utils.isNumber(userIdString)) {
                userId = Long.parseLong(userIdString);
            }
            final User user =
                    User.builder()
                            .id(userId)
                            .name(name)
                            .role(UserRole.valueOf(role))
                            .build();
            userController.update(user);
        }
        resp.setStatus(201);
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        String pathInfo = req.getRequestURI();
        String regex = "\\/(users)\\/?([0-9]+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pathInfo);
        if (matcher.find()) {
            if (Utils.isNumber(matcher.group(2))) {
                userController.delete(Long.parseLong(matcher.group(2)));
            }
        }
        resp.setStatus(201);
    }

}
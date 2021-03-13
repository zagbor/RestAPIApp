package zagbor.practice.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import zagbor.practice.controller.EventController;
import zagbor.practice.dto.EventDto;
import zagbor.practice.model.Event;
import zagbor.practice.model.EventType;
import zagbor.practice.model.File;
import zagbor.practice.util.EventUtils;
import zagbor.practice.util.Utils;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

@Slf4j
@WebServlet(urlPatterns = "/events/*")
@MultipartConfig
public class EventServlet extends HttpServlet {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    EventController eventController = new EventController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<EventDto> events;
        String json = null;
        String path = req.getRequestURI();
        String reg = "\\/(events)\\/?([0-9+])?\\/?";
        Matcher matcher = Utils.getMatcher(path, reg);
        if (matcher.find()) {
            if (matcher.group(1).equalsIgnoreCase("events") && matcher.group(2) == null) {
                String userIdString = req.getHeader("user_id");
                long userId = Long.parseLong(userIdString);
                events = eventController.getAll(userId);
                json = new ObjectMapper().writeValueAsString(events);
            } else if (matcher.group(1).equalsIgnoreCase("events") && Utils.isNumber(matcher.group(2))) {
                long eventId = Long.parseLong(matcher.group(2));
                EventDto eventDto = eventController.getById(eventId);
                json = OBJECT_MAPPER.writeValueAsString(eventDto);
            }
        }
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        if (EventUtils.requestIsValid(req)) {
            final String eventIdString = req.getParameter("event_id");
            final String eventTypeString = req.getParameter("event_type");
            final String fileIdString = req.getParameter("file_id");
            final Long eventId = Long.parseLong(eventIdString);
            final Long fileId = Long.parseLong(fileIdString);
            final EventType eventType = EventType.valueOf(eventTypeString);
            final Event event =
                    Event.builder()
                            .id(eventId)
                            .type(eventType)
                            .file(File.builder()
                                    .id(fileId)
                                    .build())
                            .date(new Date())
                            .build();
            eventController.create(event);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        if (EventUtils.requestIsValid(req)) {
            final String eventTypeString = req.getParameter("event_type");
            final String fileIdString = req.getParameter("file_id");
            final Long fileId = Long.parseLong(fileIdString);
            final EventType eventType = EventType.valueOf(eventTypeString);
            final Event event =
                    Event.builder()
                            .id(0)
                            .type(eventType)
                            .file(File.builder()
                                    .id(fileId)
                                    .build())
                            .date(new Date())
                            .build();
            eventController.create(event);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Пришел запрос {} на URI: {}", req.getMethod(), req.getRequestURI());
        final String pathInfo = req.getRequestURI();
        final String regex = "\\/(events)\\/?([0-9]+)?";
        Matcher matcher = Utils.getMatcher(pathInfo, regex);
        if (matcher.find()) {
            if (Utils.isNumber(matcher.group(2))) {
                eventController.delete(Long.parseLong(matcher.group(2)));
            }
        }

    }

}

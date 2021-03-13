package zagbor.practice.util;

import lombok.extern.slf4j.Slf4j;
import zagbor.practice.model.EventType;

import javax.servlet.http.HttpServletRequest;
@Slf4j
public class EventUtils {
    public static boolean requestIsValid(HttpServletRequest req) {
        final String eventIdString = req.getParameter("event_id");
        final String eventType = req.getParameter("event_type");
        final String fileIdString = req.getParameter("file_id");
        return eventIdString != null &&  eventIdString.length() > 0
                && eventType != null && eventType.length() > 0
                && eventTypeIsOk(eventType)
                && fileIdString != null && fileIdString.length() > 0;
    }

    private static boolean eventTypeIsOk(String eventTypeString) {
        try {
            EventType.valueOf(eventTypeString);
            return true;
        } catch (IllegalArgumentException e) {
            log.info("Такого типа EventType не существует.");
            return false;
        }
    }
}

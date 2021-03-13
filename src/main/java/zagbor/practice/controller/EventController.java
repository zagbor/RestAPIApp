package zagbor.practice.controller;

import zagbor.practice.dto.EventDto;
import zagbor.practice.model.Event;
import zagbor.practice.service.EventService;
import zagbor.practice.service.impl.EventServiceImpl;

import java.util.List;

public class EventController {
    EventService eventService = new EventServiceImpl();

    public List<EventDto> getAll() {
        return eventService.getAll();

    }

    public List<EventDto> getAll(long userId) {
        return eventService.getAll(userId);
    }

    public EventDto create(Event event) {
        return eventService.create(event);
    }

    public void delete(long eventId) {
        eventService.delete(eventId);
    }


    public EventDto getById(long eventId) {
        return eventService.getById(eventId);
    }
}

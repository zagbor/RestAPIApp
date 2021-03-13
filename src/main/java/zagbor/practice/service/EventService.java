package zagbor.practice.service;

import zagbor.practice.dto.EventDto;
import zagbor.practice.model.Event;

import java.util.List;

public interface EventService {

    List<EventDto> getAll();

    List<EventDto> getAll(long userId);

    EventDto create(Event event);

    void delete(long eventId);

    EventDto getById(long eventId);
}

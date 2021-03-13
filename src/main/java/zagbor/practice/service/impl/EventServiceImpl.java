package zagbor.practice.service.impl;

import zagbor.practice.dto.EventDto;
import zagbor.practice.dto.FileDto;
import zagbor.practice.dto.UserDto;
import zagbor.practice.model.Event;
import zagbor.practice.repository.EventRepository;
import zagbor.practice.repository.impl.EventRepositoryImpl;
import zagbor.practice.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    EventRepository eventRepository = new EventRepositoryImpl();

    @Override
    public List<EventDto> getAll() {
        List<Event> events = eventRepository.getAll();
        return buildEventsDto(events);
    }

    @Override
    public List<EventDto> getAll(long userId) {
        List<Event> events = eventRepository.getAll(userId);
        return buildEventsDto(events);
    }

    @Override
    public EventDto create(Event event) {
        Event newEvent = event.getId() == 0
                ? eventRepository.create(event) : eventRepository.update(event);
        return buildEventDto(newEvent);

    }

    @Override
    public void delete(long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public EventDto getById(long eventId) {
        Event event = eventRepository.getById(eventId).orElseThrow();
        return buildEventDto(event);
    }


    private List<EventDto> buildEventsDto(List<Event> events) {
        return events.stream()
                .map(this::buildEventDto)
                .collect(Collectors.toList());

    }

    private EventDto buildEventDto(Event event) {
        UserDto userDto = UserDto.builder()
                .id(event.getFile().getUser().getId())
                .build();

        FileDto file = FileDto.builder()
                .id(event.getId())
                .user(userDto)
                .build();

        return EventDto.builder()
                .id(event.getId())
                .date(event.getDate())
                .type(event.getType())
                .file(file)
                .build();

    }
}

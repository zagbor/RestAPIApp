package zagbor.practice.repository.impl;

import org.hibernate.query.Query;
import zagbor.practice.dto.EventDto;
import zagbor.practice.model.Event;
import zagbor.practice.model.File;
import zagbor.practice.repository.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static zagbor.practice.db.TransactionService.doInTransactionFunc;
import static zagbor.practice.db.TransactionService.doInTransactionVoid;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public Optional<Event> getById(Long id) {
        return doInTransactionFunc(session -> {
            return Optional.ofNullable(session.get(Event.class, id))
                    .map(this::buildEvent);
        });
    }


    @Override
    public List<Event> getAll() {
        return doInTransactionFunc(session -> {
            List<Event> events = session.createQuery("FROM Event", Event.class).list();
            return buildEvents(events);
        });
    }

    @Override

    public List<Event> getAll(long userId) {
        return doInTransactionFunc(session -> {
            String hql = "FROM Event e WHERE e.file.user.id = :userId";
            Query query = session.createQuery(hql);
            query.setParameter("userId", userId);
            List<Event> events = query.list();
            return buildEvents(events);
        });
    }


    @Override
    public Event create(Event event) {
        return doInTransactionFunc(session -> {
            session.save(event);
            return event;
        });
    }

    @Override
    public void deleteById(Long id) {
        doInTransactionVoid(session -> {
            Event event = Event.builder()
                    .id(id)
                    .build();
            session.delete(event);
        });
    }

    @Override
    public Event update(Event event) {
        return doInTransactionFunc(session -> {
            session.update(event);
            return event;
        });
    }

    private List<Event> buildEvents(List<Event> events) {
        return events.stream()
                .map(this::buildEvent)
                .collect(Collectors.toList());
    }


    private Event buildEvent(Event event) {
        File file =
                File.builder()
                        .id(event.getFile().getId())
                        .name(event.getFile().getName())
                        .user(event.getFile().getUser())
                        .build();

        return Event.builder()
                .id(event.getId())
                .type(event.getType())
                .file(file)
                .date(event.getDate())
                .build();

    }
}


package zagbor.practice.repository;

import zagbor.practice.model.Event;

import java.util.List;

public interface EventRepository extends GenericRepository<Event, Long> {

    List<Event> getAll(long id);

    Event update(Event event);
}

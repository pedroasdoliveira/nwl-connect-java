package br.com.nwl.Events.services;

import br.com.nwl.Events.models.Event;
import br.com.nwl.Events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired // Injeção de Dependencia
    private EventRepository eventRepository;

    public Event addNewEvent(Event event) {
        // gerando o preety name
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return (List<Event>)eventRepository.findAll();
    }

    public Event getByPrettyName(String prettyName) {
        return eventRepository.findByPrettyName(prettyName);
    }
}

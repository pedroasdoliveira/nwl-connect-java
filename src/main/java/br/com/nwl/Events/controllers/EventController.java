package br.com.nwl.Events.controllers;

import br.com.nwl.Events.models.Event;
import br.com.nwl.Events.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event newEvent) {
        return eventService.addNewEvent(newEvent);
    }

    @GetMapping("/all-events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName) {
        final Event event = eventService.getByPrettyName(prettyName);

        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(event);
    }
}

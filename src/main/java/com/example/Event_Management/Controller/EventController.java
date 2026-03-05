package com.example.Event_Management.Controller;

import com.example.Event_Management.Dto.EventDto;
import com.example.Event_Management.Dto.UserDto;
import com.example.Event_Management.Service.EventService;
import com.example.Event_Management.Service.UserService;
import com.example.Event_Management.UpdateDto.EventUpdateDto;
import com.example.Event_Management.UpdateDto.UserUpdateDto;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<EventDto> createEvent(@RequestBody @Valid EventDto dto){
        EventDto event=eventService.addEvent(dto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<EventDto>> allEvents(){
        return new ResponseEntity<>(eventService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable long id)
    {
        EventDto user=eventService.findByEventId(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/event/{name}")
    public ResponseEntity<EventDto> findAll(@PathVariable String name)
    {
        EventDto events=eventService.findAllByEventName(name);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }
    @GetMapping("/venue/{venue}")
    public ResponseEntity<List<EventDto>> findVenues(@PathVariable String venue)
    {
        List<EventDto> events=eventService.findAllByVenues(venue);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDto>> findCategories(@PathVariable String category)
    {
        List<EventDto> events=eventService.findAllByCategory(category);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<EventUpdateDto> updateEvent(@PathVariable long id, @RequestBody EventUpdateDto dto)
    {
        EventUpdateDto user=eventService.update(id,dto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id)
    {
        eventService.delete(id);
    }
}

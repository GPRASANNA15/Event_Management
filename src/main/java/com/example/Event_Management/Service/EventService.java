package com.example.Event_Management.Service;

import com.example.Event_Management.Dto.EventDto;
import com.example.Event_Management.Entity.EventEntity;
import com.example.Event_Management.Repository.EventRepository;
import com.example.Event_Management.UpdateDto.EventUpdateDto;
import jdk.jfr.Event;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;
    public EventDto addEvent(EventDto dto) {
        EventEntity event=new EventEntity();
        event.setCategory(dto.getCategory());
        event.setDate(dto.getDate());
        event.setDescription(dto.getDescription());
        event.setTime(dto.getTime());
        event.setTitle(dto.getTitle());
        event.setTotalCount(dto.getTotalCount());
        event.setVenue(dto.getVenue());
        EventEntity eventEntity=eventRepository.save(event);
        return modelMapper.map(eventEntity, EventDto.class);

    }

    public List<EventDto> findAll() {
        List<EventEntity> events=eventRepository.findAll();
        List<EventDto> eventDtos=events.stream().map(event->modelMapper.map(event, EventDto.class)).toList();
        return eventDtos;
    }

    public EventDto findByEventId(long id) {
        EventEntity event=eventRepository.findById(id).orElseThrow(()->new RuntimeException("Event ID not found"));
        return modelMapper.map(event, EventDto.class);
    }

    public EventUpdateDto update(long id, EventUpdateDto dto) {
        EventEntity event=eventRepository.findById(id).orElseThrow(()->new RuntimeException("Event ID not found"));
        if(dto.getVenue()!=null && !dto.getVenue().isEmpty()) {
            event.setVenue(dto.getVenue());
        }

        if(dto.getDate()!=null) {
            event.setDate(dto.getDate());
        }
        if(dto.getTime()!=null && !dto.getTime().isEmpty()) {
            event.setTime(dto.getTime());
        }
        if(dto.getCategory()!=null && !dto.getCategory().isEmpty()) {
            event.setCategory(dto.getCategory());
        }
        if(dto.getTotalCount()!=0) {
            event.setTotalCount(dto.getTotalCount());
        }
        if(dto.getDescription()!=null && !dto.getDescription().isEmpty()) {
            event.setDescription(dto.getDescription());
        }
        if(dto.getTitle()!=null && !dto.getTitle().isEmpty()) {
            event.setTitle(dto.getTitle());
        }
        EventEntity eventEntity=eventRepository.save(event);
        return modelMapper.map(eventEntity, EventUpdateDto.class);
    }

    public void delete(long id) {
        EventEntity event=eventRepository.findById(id).orElseThrow(()->new RuntimeException("Event Id not found"));
        eventRepository.deleteById(id);
    }

    public EventDto findAllByEventName(String name) {
        EventEntity entities=eventRepository.findByTitle(name).orElseThrow(()->new RuntimeException("Event Not Found"));

        return modelMapper.map(entities,EventDto.class);
    }

    public List<EventDto> findAllByVenues(String venue) {
        List<EventEntity> entities=eventRepository.findByVenue(venue).orElseThrow(()->new RuntimeException("Event Not fount in the venue"));
        List<EventDto> events=entities.stream().map(event->modelMapper.map(event, EventDto.class)).toList();
        return events;
    }

    public List<EventDto> findAllByCategory(String category) {
        List<EventEntity> entities=eventRepository.findByCategory(category).orElseThrow(()->new RuntimeException("Event Not found in the Category"));
        List<EventDto> events=entities.stream().map(event->modelMapper.map(event, EventDto.class)).toList();
        return events;
    }
}

package com.example.Event_Management.Service;

import com.example.Event_Management.Dto.SpeakerDto;
import com.example.Event_Management.Dto.SpeakerResponse;
import com.example.Event_Management.Entity.EventEntity;
import com.example.Event_Management.Entity.SpeakerEntity;
import com.example.Event_Management.Exception.ResourceNotFoundException;
import com.example.Event_Management.Repository.EventRepository;
import com.example.Event_Management.Repository.SpeakerRepository;
import com.example.Event_Management.UpdateDto.SpeakerUpdateDto;
import com.example.Event_Management.Utils.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpeakerService {
    @Autowired
    private SpeakerRepository speakerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EventRepository eventRepository;
    public SpeakerDto addSpeaker(SpeakerDto dto) throws IOException {
        EventEntity event=eventRepository.findById(dto.getEventId()).orElseThrow(()->new ResourceNotFoundException("SpeakerService","EventID",dto.getEventId(),"EVENT_ID_NOT_FOUND"));
                SpeakerEntity speakerEntity=new SpeakerEntity();
        speakerEntity.setDescription(dto.getDescription());
        speakerEntity.setEmail(dto.getEmail());
        speakerEntity.setContactNumber(dto.getContactNumber());
        speakerEntity.setDesignation(dto.getDesignation());
        speakerEntity.setName(dto.getName());

        String url= FileUploadUtil.saveFiles("files/profile",dto.getProfile());
        speakerEntity.setProfileUrl(url);
        SpeakerEntity speaker=speakerRepository.save(speakerEntity);
        event.getSpeakers().add(speaker);

        speaker.getEvent().add(event);


        eventRepository.save(event);
        return modelMapper.map(speaker, SpeakerDto.class);
    }

    public List<SpeakerDto> findAll() {
        List<SpeakerEntity> speakers=speakerRepository.findAll();
        List<SpeakerDto> speakerDtos=speakers.stream().map((speaker)->modelMapper.map(speaker, SpeakerDto.class)).toList();
        return speakerDtos;
    }

    public SpeakerDto findBySpeakerId(long id) {
        SpeakerEntity speakerEntity=speakerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("SpeakerService","SpeakerID",id,"SPEAKER_NOT_FOUND"));
        return modelMapper.map(speakerEntity, SpeakerDto.class);
    }

    public SpeakerUpdateDto update(long id, SpeakerUpdateDto dto) throws IOException {
        SpeakerEntity speaker=speakerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("SpeakerService","SpeakerID",id,"SPEAKER_NOT_FOUND"));
        if(dto.getProfile()!=null && !dto.getProfile().isEmpty()) {
            String url=FileUploadUtil.saveFiles("files/profile",dto.getProfile());
            speaker.setProfileUrl(url);
        }
        if(dto.getEventId()!=null )
        {
            EventEntity newEvent = eventRepository.findById(dto.getEventId())
                    .orElseThrow(() ->  new ResourceNotFoundException("SpeakerService","EventID",dto.getEventId(),"EVENT_ID_NOT_FOUND"));


            if (speaker.getEvent() != null) {
                for (EventEntity oldEvent : speaker.getEvent()) {
                    oldEvent.getSpeakers().remove(speaker);
                }
                speaker.getEvent().clear();
            }

            // add new relationship
            newEvent.getSpeakers().add(speaker);
            speaker.getEvent().add(newEvent);

            // save owner side
            eventRepository.save(newEvent);
        }
        if(dto.getName()!=null && !dto.getName().isEmpty()) {
            speaker.setName(dto.getName());
        }
        if(dto.getDesignation()!=null && !dto.getDesignation().isEmpty()) {
            speaker.setDesignation(dto.getDesignation());
        }
        if(dto.getEmail()!=null && !dto.getEmail().isEmpty()) {
            speaker.setEmail(dto.getEmail());
        }
        if(dto.getDescription()!=null && !dto.getDescription().isEmpty()) {
            speaker.setDescription(dto.getDescription());
        }
        if(dto.getContactNumber()!=null ) {
            speaker.setContactNumber(dto.getContactNumber());
        }
        SpeakerEntity update=speakerRepository.save(speaker);
        return modelMapper.map(update, SpeakerUpdateDto.class);
    }

    public void delete(long id) {
        SpeakerEntity speaker=speakerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("SpeakerService","SpeakerID",id,"SPEAKER_NOT_FOUND"));
        speakerRepository.deleteById(id);
    }

    public List<SpeakerResponse> findByEvent(long id) {
        List<SpeakerEntity> speaker=speakerRepository.findByEventId(id).orElseThrow(()->new ResourceNotFoundException("SpeakerService","EventId",id,"EVENT_NOT_FOUND"));
        List<SpeakerResponse> responses=speaker.stream().map(response->modelMapper.map(response,SpeakerResponse.class)).toList();
        return responses;
    }
}

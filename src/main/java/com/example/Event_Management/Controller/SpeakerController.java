package com.example.Event_Management.Controller;

import com.example.Event_Management.Dto.EventDto;
import com.example.Event_Management.Dto.SpeakerDto;
import com.example.Event_Management.Dto.SpeakerResponse;
import com.example.Event_Management.Service.EventService;
import com.example.Event_Management.Service.SpeakerService;
import com.example.Event_Management.UpdateDto.EventUpdateDto;
import com.example.Event_Management.UpdateDto.SpeakerUpdateDto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @PostMapping("/add")
    public ResponseEntity<SpeakerDto> createSpeaker(@ModelAttribute @Valid SpeakerDto dto) throws IOException {
        SpeakerDto event=speakerService.addSpeaker(dto);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<SpeakerDto>> allSpeakers(){
        return new ResponseEntity<>(speakerService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SpeakerDto> getSpeaker(@PathVariable long id)
    {
        SpeakerDto user=speakerService.findBySpeakerId(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<SpeakerUpdateDto> updateSpeaker(@PathVariable long id, @ModelAttribute  SpeakerUpdateDto dto) throws IOException {
        SpeakerUpdateDto user=speakerService.update(id,dto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/event/{id}")
    public ResponseEntity<List<SpeakerResponse>> getSpeakerByEvent(@PathVariable long id)
    {
        List<SpeakerResponse> speaker=speakerService.findByEvent(id);
        return new ResponseEntity<>(speaker,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSpeaker(@PathVariable long id)
    {
        speakerService.delete(id);
    }
}

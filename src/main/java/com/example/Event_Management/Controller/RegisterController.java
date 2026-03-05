package com.example.Event_Management.Controller;

import com.example.Event_Management.Dto.*;
import com.example.Event_Management.Service.RegisterService;
import com.example.Event_Management.Service.UserService;
import com.example.Event_Management.UpdateDto.RegisterUpdateDto;
import com.example.Event_Management.UpdateDto.UserUpdateDto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/add")
    public ResponseEntity<RegisterResponseDto> createUser(@RequestBody @Valid RegisterDto dto){
        RegisterResponseDto register=registerService.registerEvent(dto);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<RegisteredDetailDto>> registeredEvents(){
        return new ResponseEntity<>(registerService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RegisteredEventDto> getRegisteredEvent(@PathVariable long id)
    {
        RegisteredEventDto event=registerService.findByRegisteredId(id);
        return new ResponseEntity<>(event,HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<RegisteredEventDto>> getEvents(@PathVariable long id)
    {
        List<RegisteredEventDto> events=registerService.findByUser(id);
        return new ResponseEntity<>(events,HttpStatus.OK);
    }

    @GetMapping("/event/{name}")
    public  ResponseEntity<List<RegisteredEventUserDto>> findDetails(@PathVariable String name)
    {
        List<RegisteredEventUserDto> details=registerService.findDetailsByEvent(name);
        return new ResponseEntity<>(details,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RegisterUpdateDto> deleteRegistration(@PathVariable long id)
    {
        RegisterUpdateDto response=registerService.delete(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

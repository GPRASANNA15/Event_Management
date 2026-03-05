package com.example.Event_Management.Service;

import com.example.Event_Management.Dto.*;
import com.example.Event_Management.Entity.EventEntity;
import com.example.Event_Management.Entity.RegisterEntity;
import com.example.Event_Management.Entity.UserEntity;
import com.example.Event_Management.Repository.EventRepository;
import com.example.Event_Management.Repository.RegisterRepository;
import com.example.Event_Management.Repository.UserRepository;
import com.example.Event_Management.UpdateDto.RegisterUpdateDto;
import com.example.Event_Management.Utils.AppUtils;
import jakarta.validation.constraints.Email;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
public class RegisterService {
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;
    public RegisterResponseDto registerEvent(RegisterDto dto) {
        RegisterEntity registerEntity=new RegisterEntity();
        registerEntity.setEventId(dto.getEventId());
        registerEntity.setStatus(AppUtils.Status.REGISTERED);
        registerEntity.setUserId(dto.getUserId());
        RegisterEntity registered=registerRepository.save(registerEntity);
        RegisterResponseDto response=new RegisterResponseDto();
        EventEntity event=eventRepository.findById(dto.getEventId()).orElseThrow(()->new RuntimeException("Event Not Found"));
        response.setId(registered.getId());
        response.setRegisteredAt(registered.getRegisteredAt());
        response.setEventName(event.getTitle());
        response.setStatus(AppUtils.Status.REGISTERED.toString());
        response.setDate(event.getDate());
        UserEntity user=userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        emailService.sendResultEmail(user.getEmail());
        return response;
    }

    public List<RegisteredDetailDto> findAll() {
        List<RegisterEntity> registers=registerRepository.findAll();
        return registers.stream().map(det->{
            UserEntity user=userRepository.findById(det.getUserId()).orElseThrow(()->new RuntimeException("user not found"));
            EventEntity event=eventRepository.findById(det.getEventId()).orElseThrow(()->new RuntimeException("Event Not found"));
            RegisteredDetailDto details=new RegisteredDetailDto();
            details.setRegisteredAt(det.getRegisteredAt().toString());
            details.setRegisteredId(det.getId());
            details.setStatus(det.getStatus());
            details.setEventName(event.getTitle());
            details.setUserName(user.getUserName());
            return details;
        }).toList();
    }

    public RegisteredEventDto findByRegisteredId(long id) {
       RegisterEntity detail=registerRepository.findById(id).orElseThrow(()->new RuntimeException("Registration Id Not found"));
       RegisteredEventDto dto=new RegisteredEventDto();
       dto.setRegisteredAt(detail.getRegisteredAt());
       dto.setStatus(detail.getStatus().toString());
        EventEntity event=eventRepository.findById(detail.getEventId()).orElseThrow(()->new RuntimeException("Event Not Found"));
       dto.setEventDate(event.getDate());
       dto.setEventTitle(event.getTitle());
       dto.setEventVenue(event.getVenue());
       dto.setRegistrationId(detail.getId());
       return dto;
    }

    public RegisterUpdateDto delete(long id) {
        RegisterEntity detail=registerRepository.findById(id).orElseThrow(()->new RuntimeException("Registerid is not found"));
        detail.setStatus(AppUtils.Status.CANCELLED);
        return modelMapper.map(detail,RegisterUpdateDto.class);
    }

    public List<RegisteredEventUserDto> findDetailsByEvent(String name) {
        EventEntity event=eventRepository.findByTitle(name).orElseThrow(()->new RuntimeException("Event Name not found"));
        List<RegisterEntity> details=registerRepository.findByEventId(event.getId()).orElseThrow(()->new RuntimeException("Event ID not found"));
        return details.stream().map(det->{
            UserEntity user=userRepository.findById(det.getUserId()).orElseThrow(()->new RuntimeException("Invalid Event ID "));
            RegisteredEventUserDto dto=new RegisteredEventUserDto();
            dto.setRegisteredAt(det.getRegisteredAt());
            dto.setStatus(det.getStatus().toString());
            dto.setContactNumber(user.getContactNumber());
            dto.setUserId(user.getId());
            dto.setRegistrationId(det.getId());
            dto.setUserEmail(user.getEmail());
            return dto;
        }).toList();
    }

    public List<RegisteredEventDto> findByUser(long id) {
        List<RegisterEntity> user=registerRepository.findByUserId(id).orElseThrow(()->new RuntimeException("User Not Found"));
        return user.stream().map(event->{
            EventEntity eventdto=eventRepository.findById(event.getEventId()).orElseThrow(()->new RuntimeException("Event Not Found"));
            RegisteredEventDto dto=new RegisteredEventDto();
            dto.setRegisteredAt(event.getRegisteredAt());
            dto.setStatus(event.getStatus().toString());
            dto.setRegistrationId(event.getId());
            dto.setEventTitle(eventdto.getTitle());
            dto.setEventVenue(eventdto.getVenue());
            dto.setEventDate(eventdto.getDate());
            return dto;
        }).toList();
    }

}

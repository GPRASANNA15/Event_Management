package com.example.Event_Management.Service;

import com.example.Event_Management.Dto.*;
import com.example.Event_Management.Entity.EventEntity;
import com.example.Event_Management.Entity.RegisterEntity;
import com.example.Event_Management.Entity.UserEntity;
import com.example.Event_Management.Exception.ResourceNotFoundException;
import com.example.Event_Management.Exception.TitleNotFoundException;
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
        EventEntity event=eventRepository.findById(dto.getEventId()).orElseThrow(()->new ResourceNotFoundException("RegisterEvent","Event ID",dto.getEventId(),"EVENTID_NOT_FOUND"));
        response.setId(registered.getId());
        response.setRegisteredAt(registered.getRegisteredAt());
        response.setEventName(event.getTitle());
        response.setStatus(AppUtils.Status.REGISTERED.toString());
        response.setDate(event.getDate());
        UserEntity user=userRepository.findById(dto.getUserId()).orElseThrow(()->new ResourceNotFoundException("RegisterEvent","UserID",dto.getUserId(),"USER_NOT_FOUND"));
        emailService.sendResultEmail(user.getEmail());
        return response;
    }

    public List<RegisteredDetailDto> findAll() {
        List<RegisterEntity> registers=registerRepository.findAll();
        return registers.stream().map(det->{
            UserEntity user=userRepository.findById(det.getUserId()).orElseThrow(()->new ResourceNotFoundException("findAllRegistration","UserID",det.getUserId(),"USER_NOT_FOUND"));
            EventEntity event=eventRepository.findById(det.getEventId()).orElseThrow(()->new ResourceNotFoundException("findAllRegistration","Event ID",det.getEventId(),"EVENTID_NOT_FOUND"));
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
       RegisterEntity detail=registerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("FindByRegisteredId","Register ID",id,"EVENTID_NOT_FOUND"));
       RegisteredEventDto dto=new RegisteredEventDto();
       dto.setRegisteredAt(detail.getRegisteredAt());
       dto.setStatus(detail.getStatus().toString());
       EventEntity event=eventRepository.findById(detail.getEventId()).orElseThrow(()->new ResourceNotFoundException("FindByRegisteredId","Event ID",detail.getEventId(),"EVENTID_NOT_FOUND"));
       dto.setEventDate(event.getDate());
       dto.setEventTitle(event.getTitle());
       dto.setEventVenue(event.getVenue());
       dto.setRegistrationId(detail.getId());
       return dto;
    }

    public RegisterUpdateDto delete(long id) {
        RegisterEntity detail=registerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("DeleteRegister","RegistrationID",id,"REGISTERID_NOT_FOUND"));
        detail.setStatus(AppUtils.Status.CANCELLED);
        RegisterEntity saved=registerRepository.save(detail);
        return modelMapper.map(saved,RegisterUpdateDto.class);
    }

    public List<RegisteredEventUserDto> findDetailsByEvent(String name) {
        EventEntity event=eventRepository.findByTitle(name).orElseThrow(()->new TitleNotFoundException("findDetailsByEvent","Event Title",name,"EVENT_NOT_FOUND"));
        List<RegisterEntity> details=registerRepository.findByEventId(event.getId()).orElseThrow(()->new ResourceNotFoundException("FindDetailsByEvent","EventID", event.getId(),"EVENTID_NOT_FOUND"));
        return details.stream().map(det->{
            UserEntity user=userRepository.findById(det.getUserId()).orElseThrow(()->new ResourceNotFoundException("FindDetailsByEvent","UserId", det.getUserId(),"USER_NOT_FOUND"));
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
        List<RegisterEntity> user=registerRepository.findByUserId(id).orElseThrow(()->new  ResourceNotFoundException("FindByUser","UserId", id,"USER_NOT_FOUND"));
        return user.stream().map(event->{
            EventEntity eventdto=eventRepository.findById(event.getEventId()).orElseThrow(()->new ResourceNotFoundException("FindByUser","EventID", event.getEventId(),"EVENT_NOT_FOUND"));
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

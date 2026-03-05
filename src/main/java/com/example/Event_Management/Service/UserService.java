package com.example.Event_Management.Service;

import com.example.Event_Management.Dto.AuthDto;
import com.example.Event_Management.Dto.AuthResponse;
import com.example.Event_Management.Dto.UserDto;
import com.example.Event_Management.Entity.UserEntity;
import com.example.Event_Management.Repository.UserRepository;
import com.example.Event_Management.UpdateDto.UserUpdateDto;
import com.example.Event_Management.Utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserDto addUser(UserDto dto) {
        UserEntity user=new UserEntity();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setContactNumber(dto.getContactNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserEntity saved=userRepository.save(user);
        return modelMapper.map(saved, UserDto.class);
    }

    public List<UserDto> findAll() {
        List<UserEntity> users=userRepository.findAll();
        List<UserDto> userDet=users.stream().map(user->modelMapper.map(user, UserDto.class)).toList();
        return userDet;
    }

    public UserDto findByUserId(long id) {
        UserEntity user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not found"));
        return modelMapper.map(user, UserDto.class);
    }

    public UserUpdateDto update(long id, UserUpdateDto dto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid User Id"));
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getContactNumber() != null) {
            user.setContactNumber(dto.getContactNumber());
        }
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
            user.setUserName(dto.getUserName());
        }
        UserEntity userEntity=userRepository.save(user);
        return modelMapper.map(userEntity, UserUpdateDto.class);
    }

    public void delete(long id) {
            UserEntity user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
             userRepository.deleteById(id);
    }

    public AuthResponse auth(AuthDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );


        UserEntity user = userRepository.findByEmail(
                dto.getEmail()
        ).orElseThrow(() -> new RuntimeException("User not found"));


        String token = jwtUtil.generateToken(user.getEmail());


        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setId(user.getId());
        response.setRole(user.getRole().toString());
        response.setEmail(user.getEmail());

        return response;
    }
}

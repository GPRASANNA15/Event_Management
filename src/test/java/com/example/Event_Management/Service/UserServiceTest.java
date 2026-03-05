package com.example.Event_Management.Service;


import com.example.Event_Management.Dto.UserDto;
import com.example.Event_Management.Entity.UserEntity;
import com.example.Event_Management.Repository.UserRepository;
import com.example.Event_Management.Utils.AppUtils;
import com.example.Event_Management.Utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest
{
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;
    private UserEntity userEntity;
    @BeforeEach
    public void setUp()
    {
        userEntity=new UserEntity();
        userEntity.setEmail("john@gmail.com");
        userEntity.setRole(AppUtils.Roles.ADMIN);
        userEntity.setUserName("john");
        userEntity.setPassword("john123@");
        userEntity.setContactNumber(7418529638L);
    }

    @Test
    public void givenUserDto_whenSaved_thenReturnSavedDto() {

        UserDto userDto = new UserDto();
        userDto.setEmail("john@gmail.com");
        userDto.setUserName("john");
        userDto.setPassword("john123@");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("john@gmail.com");
        userEntity.setUserName("john");
        userEntity.setPassword("encodedPassword");

        given(passwordEncoder.encode(any()))
                .willReturn("encodedPassword");
        given(userRepository.save(any(UserEntity.class)))
                .willReturn(userEntity);


        given(modelMapper.map(any(UserEntity.class), any(Class.class)))
                .willReturn(userDto);


        UserDto savedDto = userService.addUser(userDto);


        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getEmail()).isEqualTo("john@gmail.com");
    }
}

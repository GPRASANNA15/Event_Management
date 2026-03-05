package com.example.Event_Management.Repository;


import com.example.Event_Management.Entity.UserEntity;
import com.example.Event_Management.Utils.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest
{

    @Autowired
    private  UserRepository userRepository;

    private UserEntity userEntity;


    @BeforeEach
    public void setup(){
        userEntity=new UserEntity();
        userEntity.setUserName("doe");
        userEntity.setRole(AppUtils.Roles.ADMIN);
        userEntity.setPassword("123Doe@!");
        userEntity.setEmail("doe@gmail.com");
        userEntity.setContactNumber(7894563218L);
    }
    @Test
    public void givenUserObject_whenSaved_thenReturnSavedUser(){
        UserEntity saved=userRepository.save(userEntity);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
    }
    @Test
    public void givenUserEmailAndPassword_whenChecked_thenReturnUserDetails(){
        userRepository.save(userEntity);
        UserEntity userDetails=userRepository.findByEmailAndPassword(userEntity.getEmail(),userEntity.getPassword());
        assertThat(userDetails).isNotNull();

    }
}

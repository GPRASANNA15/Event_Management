package com.example.Event_Management.Controller;

import com.example.Event_Management.Dto.AuthDto;
import com.example.Event_Management.Dto.AuthResponse;
import com.example.Event_Management.Dto.UserDto;
import com.example.Event_Management.Service.UserService;
import com.example.Event_Management.UpdateDto.UserUpdateDto;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto dto){
        UserDto user=userService.addUser(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> allUsers(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id)
    {
        UserDto user=userService.findByUserId(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateDto> updateUser(@PathVariable long id, @RequestBody UserUpdateDto dto)
    {
        UserUpdateDto user=userService.update(id,dto);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id)
    {
        userService.delete(id);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authUser(@RequestBody AuthDto dto){
        return new ResponseEntity<>(userService.auth(dto),HttpStatus.OK);
    }
}

package com.example.Event_Management.Controller;


import com.example.Event_Management.Dto.UserDto;
import com.example.Event_Management.Service.UserService;
import com.example.Event_Management.Utils.AppUtils;
import com.example.Event_Management.Utils.JwtTokenFilter;
import com.example.Event_Management.Utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
public class UserControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;

    @BeforeEach
    void setup() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUserName("John Doe");
        userDto.setEmail("john@example.com");
        userDto.setPassword("P@ssw0rd123");
        userDto.setRole(AppUtils.Roles.ADMIN);
        userDto.setContactNumber(7896542589L);
    }

    @Test
    void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception {


        given(userService.addUser(any(UserDto.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value(userDto.getUserName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void givenInvalidUser_whenCreateUser_thenReturnBadRequest() throws Exception {

        UserDto invalidUser = new UserDto();

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
}

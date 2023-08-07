package org.eightsleep.controller;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.eightsleep.model.Role;
import org.eightsleep.model.User;
import org.eightsleep.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetUsersByHouseholdId_Success() throws Exception {
        Long householdId = 0L;
        List<User> users = Arrays.asList(
                new User(1L, 0L, "Alice", Role.PRIMARY),
                new User(2L, 0L, "Bob", Role.LIMITED)
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/household/{householdId}/users", householdId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Alice")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", is("Bob")));
    }

    @Test
    public void testGetUsersByHouseholdId_NotFound() throws Exception {
        Long householdId = 123L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/household/{householdId}/users", householdId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("No users found with household ID: " + householdId)));
    }

    @Test
    public void testGetUserById_Success() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Alice")));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        Long userId = 123L;

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("User not found with ID: " + userId)));
    }
}
package org.eightsleep.controller;

import org.eightsleep.model.User;
import org.eightsleep.service.SleepDataServiceImpl;
import org.eightsleep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1")
public class UserController {
    private static final Logger logger = Logger.getLogger(SleepDataServiceImpl.class.getName());
    @Autowired
    private UserService userService;

    @GetMapping("/household/{householdId}/users")
    public List<User> getUsersByHouseholdId(@PathVariable Long householdId) {
        return userService.getUsersByHouseholdId(householdId);
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

}

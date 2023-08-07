package org.eightsleep.controller;

import org.eightsleep.model.User;
import org.eightsleep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/household/{householdId}/users")
    public List<User> getUsersByHouseholdId(@PathVariable Long householdId) {
        return userService.getUsersByHouseholdId(householdId);
    }

    @GetMapping("/user/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}

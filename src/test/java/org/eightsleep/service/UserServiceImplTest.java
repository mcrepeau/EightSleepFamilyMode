package org.eightsleep.service;

import org.eightsleep.model.Role;
import org.eightsleep.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl();
    }

    @Test
    public void testGetUsersByHouseholdIdWithExistingHouseholdId() {
        Long householdId = 0L;

        List<User> result = userService.getUsersByHouseholdId(householdId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Alice", result.get(0).getName());
        Assertions.assertEquals(Role.PRIMARY, result.get(0).getRole());
        Assertions.assertEquals("Bob", result.get(1).getName());
        Assertions.assertEquals(Role.LIMITED, result.get(1).getRole());
    }

    @Test
    public void testGetUsersByHouseholdIdWithNonExistingHouseholdId() {
        Long householdId = 4L; // Non-existing householdId

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUsersByHouseholdId(householdId));
    }

    @Test
    public void testGetUserByIdWithExistingUserId() {
        Long userId = 1L;

        User result = userService.getUserById(userId);

        Assertions.assertEquals("Alice", result.getName());
        Assertions.assertEquals(Role.PRIMARY, result.getRole());
    }

    @Test
    public void testGetUserByIdWithNonExistingUserId() {
        Long userId = 4L; // Non-existing userId

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));
    }
}

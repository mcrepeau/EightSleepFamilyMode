package org.eightsleep.service;

import org.eightsleep.model.Role;
import org.eightsleep.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    // Dummy user data
    final List<User> users = Arrays.asList(
            new User(1L, 0L, "Alice", Role.PRIMARY),
            new User(2L, 0L, "Bob", Role.LIMITED),
            new User(3L, 1L, "Charlie", Role.LIMITED)
    );
    @Override
    public List<User> getUsersByHouseholdId(Long householdId) {
        List<User> usersByHouseholdId = users.stream()
                .filter(user -> Objects.equals(user.getHouseholdId(), householdId))
                .toList();

        if (usersByHouseholdId.isEmpty()) {
            throw new EntityNotFoundException("No users found with household ID: " + householdId);
        }

        return usersByHouseholdId;
    }
    @Override
    public User getUserById(Long userId) throws EntityNotFoundException {
        Optional<User> user = users.stream()
                .filter(u -> Objects.equals(u.getId(), userId))
                .findFirst();

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
    }
}

package org.eightsleep.service;

import org.eightsleep.model.Role;
import org.eightsleep.model.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    // Dummy user data
    private final List<User> users = Arrays.asList(
            new User(1, 0, "Alice", Role.PRIMARY),
            new User(2, 0, "Bob", Role.LIMITED),
            new User(3, 1, "Charlie", Role.LIMITED)
    );
    public List<User> getUsersByHouseholdId(Long householdId) {
        return users.stream()
                .filter(user -> user.getHouseholdId() == householdId)
                .collect(Collectors.toList());
    }

    public Optional<User> getUserById(Long userId) {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst();
    }
}

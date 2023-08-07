package org.eightsleep.service;

import org.eightsleep.model.Role;
import org.eightsleep.model.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        return users.stream()
                .filter(user -> Objects.equals(user.getHouseholdId(), householdId))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<User> getUserById(Long userId) {
        return users.stream()
                .filter(user -> Objects.equals(user.getId(), userId))
                .findFirst();
    }
}

package org.eightsleep.service;

import org.eightsleep.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsersByHouseholdId(Long householdId);
    Optional<User> getUserById(Long userId);
}

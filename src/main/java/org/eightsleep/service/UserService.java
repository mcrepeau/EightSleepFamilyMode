package org.eightsleep.service;

import org.eightsleep.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsersByHouseholdId(Long householdId);
    User getUserById(Long userId) throws EntityNotFoundException;
}

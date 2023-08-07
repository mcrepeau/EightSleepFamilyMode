package org.eightsleep.model;

import lombok.Value;

@Value
public class User {
    Long id;
    Long householdId;
    String name;
    Role role;
}

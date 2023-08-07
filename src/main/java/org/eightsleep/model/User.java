package org.eightsleep.model;

import lombok.Value;

@Value
public class User {
    int id;
    int householdId;
    String name;
    Role role;
}

package org.eightsleep.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
@Data
@NoArgsConstructor
public class SleepData {
    List<Interval> intervals;
}

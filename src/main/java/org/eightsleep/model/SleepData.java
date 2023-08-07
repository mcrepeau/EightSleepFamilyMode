package org.eightsleep.model;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SleepData {
    List<Interval> intervals;
}

package org.eightsleep.model;

import lombok.Value;

@Value
public class TimeSeriesData {
    String timestamp;
    double value;
}

package org.eightsleep.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.eightsleep.serialization.TimeSeriesDeserializer;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Interval {
    String id;
    String ts;
    List<Stage> stages;
    int score;
    @JsonDeserialize(using = TimeSeriesDeserializer.class)
    Map<String, List<TimeSeriesData>> timeseries;
}
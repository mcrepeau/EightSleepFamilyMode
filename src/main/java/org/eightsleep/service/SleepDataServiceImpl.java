package org.eightsleep.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.eightsleep.model.Interval;
import org.eightsleep.model.SleepData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SleepDataServiceImpl implements SleepDataService {
    private static final Logger logger = Logger.getLogger(SleepDataServiceImpl.class.getName());
    ObjectMapper objectMapper = new ObjectMapper();
    SleepData sleepData = new SleepData();
    @Override
    public SleepData getUserSleepData(Long userId, String startTs, String endTs) throws IOException {
        Pair<Instant, Instant> timestampRange = parseTs(startTs, endTs);

        try (InputStream inputStream = this.getClass().getResourceAsStream("/" + userId + ".json")) {
            if (inputStream == null) {
                logger.warning("Failed to read the JSON file for user " + userId);
                throw new EntityNotFoundException("Sleep data could not be found for user with ID: " + userId);
            }

            sleepData = objectMapper.readValue(inputStream, SleepData.class);
        }
        List<Interval> sleepDataIntervals = sleepData.getIntervals();
        List<Interval> sleepDataIntervalsForDates = new ArrayList<>();
        for (Interval sleepInterval: sleepDataIntervals) {
            Instant timestamp = Instant.parse(sleepInterval.getTs());
            if (timestamp.isAfter(timestampRange.getLeft()) && timestamp.isBefore(timestampRange.getRight())) {
                sleepDataIntervalsForDates.add(sleepInterval);
            }
        }
        return new SleepData(sleepDataIntervalsForDates);
    }

    private Pair<Instant, Instant> parseTs(String startTs, String endTs) {
        try {
            Instant startTimestamp = startTs != null ? Instant.parse(startTs) : Instant.MIN;
            Instant endTimestamp = endTs != null ? Instant.parse(endTs) : Instant.MAX;
            return Pair.of(startTimestamp, endTimestamp);
        } catch (DateTimeParseException e) {
            logger.severe("Cannot parse the timestamps provided: " + e.getParsedString());
            throw e;
        }
    }
}

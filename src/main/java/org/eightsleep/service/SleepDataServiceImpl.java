package org.eightsleep.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eightsleep.model.Interval;
import org.eightsleep.model.SleepData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class SleepDataServiceImpl implements SleepDataService {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public SleepData getUserSleepData(Long userId) throws IOException {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/" + userId + ".json")) {
            if (inputStream == null) {
                throw new IOException("Failed to read the JSON file for user " + userId);
            }

            return objectMapper.readValue(inputStream, SleepData.class);
        }

    }
}

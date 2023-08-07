package org.eightsleep.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eightsleep.model.Interval;
import org.eightsleep.model.SleepData;
import org.eightsleep.model.Stage;
import org.eightsleep.model.TimeSeriesData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class SleepDataServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private SleepDataServiceImpl sleepDataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserSleepDataWithValidInput() throws IOException {
        Long userId = 1L;
        String startTs = "2023-08-07T00:00:00Z";
        String endTs = "2023-08-08T00:00:00Z";

        List<TimeSeriesData> timeSeriesDataList = Arrays.asList(
                new TimeSeriesData("2023-08-07T05:00:00", 10.5),
                new TimeSeriesData("2023-08-07T06:00:00", 15.2)
        );

        Interval interval = new Interval();
        interval.setId("interval1");
        interval.setTs("2023-08-07T05:00:00Z");
        interval.setStages(List.of(new Stage()));
        interval.setScore(90);
        Map<String, List<TimeSeriesData>> timeseries = new HashMap<>();
        timeseries.put("key1", timeSeriesDataList);
        interval.setTimeseries(timeseries);

        SleepData sleepData = new SleepData();
        sleepData.setIntervals(List.of(interval));

        when(objectMapper.readValue(any(InputStream.class), eq(SleepData.class))).thenReturn(sleepData);

        SleepData result = sleepDataService.getUserSleepData(userId, startTs, endTs);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getIntervals().size());

        Interval resultInterval = result.getIntervals().get(0);
        Assertions.assertEquals("interval1", resultInterval.getId());
        Assertions.assertEquals("2023-08-07T05:00:00Z", resultInterval.getTs());
        Assertions.assertEquals(1, resultInterval.getStages().size());
        Assertions.assertEquals(90, resultInterval.getScore());
        Assertions.assertEquals(1, resultInterval.getTimeseries().size());
        Assertions.assertEquals(timeSeriesDataList, resultInterval.getTimeseries().get("key1"));
    }

    @Test
    public void testGetUserSleepDataWithMissingFile() throws IOException {
        Long userId = 456L;
        String startTs = "2023-08-07T00:00:00Z";
        String endTs = "2023-08-08T00:00:00Z";

        when(objectMapper.readValue(any(InputStream.class), eq(SleepData.class))).thenReturn(new SleepData());

        Assertions.assertThrows(EntityNotFoundException.class, () -> sleepDataService.getUserSleepData(userId, startTs, endTs));
    }

    @Test
    public void testGetUserSleepDataWithInvalidDates() throws IOException {
        Long userId = 1L;
        String startTs = "2023-08-08T00:00:00Z"; // Later than all intervals
        String endTs = "2023-08-09T00:00:00Z"; // Later than all intervals

        List<TimeSeriesData> timeSeriesDataList = Arrays.asList(
                new TimeSeriesData("2023-08-07T05:00:00", 10.5)
        );

        Interval interval = new Interval();
        interval.setId("interval1");
        interval.setTs("2023-08-07T05:00:00Z");
        interval.setStages(List.of(new Stage()));
        interval.setScore(90);
        Map<String, List<TimeSeriesData>> timeseries = new HashMap<>();
        timeseries.put("key1", timeSeriesDataList);
        interval.setTimeseries(timeseries);

        SleepData sleepData = new SleepData();
        sleepData.setIntervals(List.of(interval));

        when(objectMapper.readValue(any(InputStream.class), eq(SleepData.class))).thenReturn(sleepData);

        SleepData result = sleepDataService.getUserSleepData(userId, startTs, endTs);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getIntervals().isEmpty());
    }
}
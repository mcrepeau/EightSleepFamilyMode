package org.eightsleep.serialization;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eightsleep.model.TimeSeriesData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeSeriesDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testDeserializeWithValidInput() throws IOException {
        String json = "{ \"series1\": [ [\"2023-08-07T12:00:00\", 10.5], [\"2023-08-07T13:00:00\", 15.2] ], " +
                "\"series2\": [ [\"2023-08-07T14:00:00\", 20.0], [\"2023-08-07T15:00:00\", 5.7] ] }";

        JsonFactory factory = objectMapper.getFactory();
        JsonParser parser = factory.createParser(json);
        TimeSeriesDeserializer deserializer = new TimeSeriesDeserializer();

        Map<String, List<TimeSeriesData>> result = deserializer.deserialize(parser, objectMapper.getDeserializationContext());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.containsKey("series1"));
        Assertions.assertTrue(result.containsKey("series2"));

        List<TimeSeriesData> series1 = result.get("series1");
        List<TimeSeriesData> series2 = result.get("series2");

        Assertions.assertEquals(2, series1.size());
        Assertions.assertEquals(2, series2.size());
        Assertions.assertEquals("2023-08-07T12:00:00", series1.get(0).getTimestamp());
        Assertions.assertEquals(10.5, series1.get(0).getValue(), 0.001);
        Assertions.assertEquals("2023-08-07T14:00:00", series2.get(0).getTimestamp());
        Assertions.assertEquals(20.0, series2.get(0).getValue(), 0.001);
    }

    @Test
    public void testDeserializeWithEmptyInput() throws IOException {
        String json = "{}";

        JsonFactory factory = objectMapper.getFactory();
        JsonParser parser = factory.createParser(json);
        TimeSeriesDeserializer deserializer = new TimeSeriesDeserializer();

        Map<String, List<TimeSeriesData>> result = deserializer.deserialize(parser, objectMapper.getDeserializationContext());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testDeserializeWithInvalidDataFormat() throws IOException {
        String json = "{ \"series1\": [ [\"2023-08-07T12:00:00\", 10.5], [\"2023-08-07T13:00:00\", \"invalid\"] ] }";

        JsonFactory factory = objectMapper.getFactory();
        JsonParser parser = factory.createParser(json);
        TimeSeriesDeserializer deserializer = new TimeSeriesDeserializer();

        Map<String, List<TimeSeriesData>> result = deserializer.deserialize(parser, objectMapper.getDeserializationContext());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("series1"));

        List<TimeSeriesData> series1 = result.get("series1");
        Assertions.assertEquals(1, series1.size());
        Assertions.assertEquals("2023-08-07T12:00:00", series1.get(0).getTimestamp());
        Assertions.assertEquals(10.5, series1.get(0).getValue(), 0.001);

    }

    @Test
    public void testDeserializeWithMissingData() throws IOException {
        String json = "{ \"series1\": [ [\"2023-08-07T12:00:00\", 10.5], [\"2023-08-07T13:00:00\"] ] }";

        JsonFactory factory = objectMapper.getFactory();
        JsonParser parser = factory.createParser(json);
        TimeSeriesDeserializer deserializer = new TimeSeriesDeserializer();

        Map<String, List<TimeSeriesData>> result = deserializer.deserialize(parser, objectMapper.getDeserializationContext());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("series1"));

        List<TimeSeriesData> series1 = result.get("series1");
        Assertions.assertEquals(1, series1.size());
        Assertions.assertEquals("2023-08-07T12:00:00", series1.get(0).getTimestamp());
        Assertions.assertEquals(10.5, series1.get(0).getValue(), 0.001);
    }
}

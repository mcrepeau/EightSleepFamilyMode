package org.eightsleep.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.eightsleep.model.TimeSeriesData;
import org.eightsleep.service.SleepDataServiceImpl;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class TimeSeriesDeserializer extends JsonDeserializer<Map<String, List<TimeSeriesData>>> {
    private static final Logger logger = Logger.getLogger(TimeSeriesDeserializer.class.getName());
    @Override
    public Map<String, List<TimeSeriesData>> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Map<String, List<TimeSeriesData>> timeseries = new HashMap<>();

        JsonNode timeSeriesNode = jsonParser.getCodec().readTree(jsonParser);
        Iterator<Map.Entry<String, JsonNode>> fields = timeSeriesNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode valueNode = entry.getValue();

            if (valueNode.isArray()) {
                List<TimeSeriesData> entries = new ArrayList<>();
                for (JsonNode entryNode : valueNode) {
                    if (entryNode.isArray() && entryNode.size() == 2) {
                        try {
                            String timestamp = entryNode.get(0).asText();
                            double value = Double.parseDouble(String.valueOf(entryNode.get(1)));
                            TimeSeriesData timeSeriesData = new TimeSeriesData(timestamp, value);
                            entries.add(timeSeriesData);
                        } catch (NumberFormatException e) {
                            logger.warning("Could not parse value: " + entryNode.get(1) + " into a Double");
                        }
                    }
                }
                timeseries.put(key, entries);
            }
        }

        return timeseries;
    }
}

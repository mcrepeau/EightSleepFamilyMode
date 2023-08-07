package org.eightsleep.service;

import org.eightsleep.model.Interval;
import org.eightsleep.model.SleepData;

import java.io.IOException;
import java.util.List;

public interface SleepDataService {
    SleepData getUserSleepData(Long userId) throws IOException;
}

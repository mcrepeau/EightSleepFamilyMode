package org.eightsleep.controller;

import org.eightsleep.model.Interval;
import org.eightsleep.model.SleepData;
import org.eightsleep.service.SleepDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class SleepDataController {
    @Autowired
    private SleepDataService sleepDataService;

    @GetMapping("/sleepData/{userId}")
    public SleepData getSleepDataForUser(@PathVariable Long userId) throws IOException {
        return sleepDataService.getUserSleepData(userId);
    }
}

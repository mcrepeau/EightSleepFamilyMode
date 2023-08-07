package org.eightsleep.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.eightsleep.model.SleepData;
import org.eightsleep.service.EntityNotFoundException;
import org.eightsleep.service.SleepDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SleepDataController.class)
public class SleepDataControllerTest {

    @Mock
    private SleepDataService sleepDataService;

    @InjectMocks
    private SleepDataController sleepDataController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetSleepDataForUser_Success() throws Exception {
        Long userId = 1L;
        String startTs = "2017-03-08T06:00:00.000Z";
        String endTs = "2023-01-02T00:00:00Z";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/sleepData/{userId}", userId)
                        .param("startTs", startTs)
                        .param("endTs", endTs)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetSleepDataForUser_EntityNotFoundException() throws Exception {
        Long userId = 123L;
        String startTs = "2023-01-01T00:00:00Z";
        String endTs = "2023-01-02T00:00:00Z";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/sleepData/{userId}", userId)
                        .param("startTs", startTs)
                        .param("endTs", endTs)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        is("Sleep data could not be found for user with ID: " + userId)));
    }
}
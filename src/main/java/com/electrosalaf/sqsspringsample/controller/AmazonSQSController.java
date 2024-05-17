package com.electrosalaf.sqsspringsample.controller;

import com.electrosalaf.sqsspringsample.request.NotificationServiceRequest;
import com.electrosalaf.sqsspringsample.service.AmazonSQSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ibrahim Lawal
 * @createdOn 18/March/2024
 */

@RestController
@RequiredArgsConstructor
@Slf4j
public class AmazonSQSController {

    private final AmazonSQSService amazonSQSService;

    @PostMapping({"/put/{message}"})
    public void putMessageToQueue(@PathVariable(value = "message") String message) {
        amazonSQSService.putMessageToQueue(message);
    }

    @PostMapping({"/send-sms-and-forward"})
    public void writeMessageToQueue(@RequestBody NotificationServiceRequest notificationServiceRequest) {
        amazonSQSService.sendSmsAndForwardToQueue(notificationServiceRequest);
    }


}

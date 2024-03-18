package com.electrosalaf.sqsspringsample.controller;

import com.electrosalaf.sqsspringsample.service.AmazonSQSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ibrahim Lawal
 * @createdOn 18/March/2024
 */

@RestController
@RequiredArgsConstructor
@Slf4j
public class AmazonSQSController {

    @Value("${cloud.aws.end-point.uri}")
    private String awsUri;

    private final AmazonSQSService amazonSQSService;

    @PostMapping({"/put/{message}"})
    public void putMessageToQueue(@PathVariable(value = "message") String message) {
        amazonSQSService.putMessageToQueue(message);
    }

    @PostMapping({"/write/{message}"})
    public void writeMessageToQueue(@PathVariable(value = "message") String message) {
        amazonSQSService.writeMessageToQueue(message);
    }


}

package com.electrosalaf.sqsspringsample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author Ibrahim Lawal
 * @createdOn 18/March/2024
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class AmazonSQSService {

    @Value("${app.queue.name}")
    private String queueName;

    @Value("${cloud.aws.end-point.uri}")
    private String awsUri;

    private final QueueMessagingTemplate queueMessagingTemplate;

    public void putMessageToQueue(String message) {
        queueMessagingTemplate.send(awsUri, MessageBuilder.withPayload(message).build());
    }

    public void writeMessageToQueue(String message) {
        queueMessagingTemplate.convertAndSend(queueName, message);
    }

    @SqsListener(value = "${cloud.aws.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void readMessageFromQueue(String message) {
        log.info("Message read from queue: {}", message);
        System.out.println("Message read from queue: " + message);
    }


}


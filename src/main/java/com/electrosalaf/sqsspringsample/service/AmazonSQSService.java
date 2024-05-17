package com.electrosalaf.sqsspringsample.service;

import com.electrosalaf.sqsspringsample.request.NotificationServiceRequest;
import com.electrosalaf.sqsspringsample.request.SwopSMSBody;
import com.electrosalaf.sqsspringsample.request.SwopSMSRequest;
import com.electrosalaf.sqsspringsample.response.NotificationServiceResponse;
import com.electrosalaf.sqsspringsample.response.SwopSMSResponse;
import com.electrosalaf.sqsspringsample.util.ClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.electrosalaf.sqsspringsample.service.ApiClientService.generateBasicAuth;

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

    @Value("${default.sender.mail:}")
    private String defaultSender;

    @Value("${swop.sms.url:}")
    private String smsUrl;

    @Value("${swop.sms.username:}")
    private String smsUsername;

    @Value("${swop.sms.password:}")
    private String smsPassword;

    private final QueueMessagingTemplate queueMessagingTemplate;

    private final ApiClientService apiClientService;

    public void putMessageToQueue(Object message) {
        queueMessagingTemplate.send(awsUri, MessageBuilder.withPayload(message).build());
    }

    public void writeMessageToQueue(String message) {
        queueMessagingTemplate.convertAndSend(queueName, message);
    }

    @SqsListener(value = "${app.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void readMessageFromQueue(String message) {
        log.info("Message read from queue: {}", message);
    }

    public NotificationServiceResponse sendSmsAndForwardToQueue(NotificationServiceRequest notificationServiceRequest) {
        SwopSMSBody swopSmsBody = SwopSMSBody.builder()
                .id(generateRandomRequestId())
                .receiver(notificationServiceRequest.getRequesterPhoneNumber())
                .sender("SWOP")
                .message("Kindly enter this OTP {} to complete your registration on the swop app".replace("{}", notificationServiceRequest.getUserCode()))
                .type("sms")
                .build();

        SwopSMSRequest smsRequest = SwopSMSRequest.builder()
                .sms(List.of(swopSmsBody))
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", generateBasicAuth(smsUsername, smsPassword));

        try {
            SwopSMSResponse response = apiClientService.postRequestWithHeaders(smsUrl, ClientUtil.getGsonMapper().toJson(smsRequest), httpHeaders, SwopSMSResponse.class).getBody();
            log.info("The sms response : {}", response);

            // Send Message to the queue

            putMessageToQueue(notificationServiceRequest);
        } catch (Exception e) {
            log.error("Error occurred while sending SMS and forwarding to queue: {}", e.getMessage());
            e.printStackTrace();
        }

        return new NotificationServiceResponse();
    }

    private String generateRandomRequestId(){
        return "swop".concat(RandomStringUtils.randomAlphanumeric(5));
    }

}


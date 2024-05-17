package com.electrosalaf.sqsspringsample.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ibrahim Lawal
 * @createdOn 15/March/2024
 */


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationServiceRequest {

    private String sender;

    private List<String> recipients = new ArrayList<>();
    private String receiver;
    private String notificationContent;
    private String notificationSubject;
    private long smsId;
    private String notificationRequestType = NotificationRequestType.UNKNOWN;
    private List<String> bccList = new ArrayList<>();
    private List<String> ccList = new ArrayList<>();
    private String userCode;

    private String registrationCode;

    private List<String> phoneNumbers = new ArrayList<>();
    private String requesterPhoneNumber;

    private boolean isHtml;
    private String channel;

}

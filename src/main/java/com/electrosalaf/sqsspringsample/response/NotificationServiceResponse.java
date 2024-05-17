package com.electrosalaf.sqsspringsample.response;


import com.electrosalaf.sqsspringsample.constants.NotificationResponseCodes;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


/**
 * @author Ibrahim Lawal
 * @createdOn 15/March/2024
 */

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
@NoArgsConstructor
public class NotificationServiceResponse {
    private String narration;
    private NotificationResponseCodes notificationServiceResponseCode;
    private Map<String, Object> data;

    public static NotificationServiceResponse fromCodeAndNarration(NotificationResponseCodes notificationResponseCodes, String msg) {
        NotificationServiceResponse notificationServiceResponse = new NotificationServiceResponse();
        notificationServiceResponse.setNarration(msg);
        notificationServiceResponse.setNotificationServiceResponseCode(notificationResponseCodes);
        return notificationServiceResponse;
    }
}

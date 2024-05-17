package com.electrosalaf.sqsspringsample.request;


/**
 * @author Ibrahim Lawal
 * @createdOn 18/March/2024
 */

public class NotificationRequestType {

    private NotificationRequestType() {
        throw new IllegalStateException("NotificationRequestType Utility");
    }
    public static final String SEND_SMS = "SEND_SMS";
    public static final String UNKNOWN = "UNKNOWN";


}

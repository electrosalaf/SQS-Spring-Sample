package com.electrosalaf.sqsspringsample.constants;


/**
 * @author Ibrahim Lawal
 * @createdOn 15/March/2024
 */

public enum NotificationResponseCodes {

        COMPLETED,
        ERROR,
        INVALID_REQUEST,
        FAILED;

        public static NotificationResponseCodes fromString(String text) {

            if (text != null) {
                for (NotificationResponseCodes b : NotificationResponseCodes.values()) {
                    if (text.equalsIgnoreCase(b.name())) {
                        return b;
                    }
                }
            }

            throw new IllegalArgumentException(String.format("Unknown enum type %s", text));
        }
}

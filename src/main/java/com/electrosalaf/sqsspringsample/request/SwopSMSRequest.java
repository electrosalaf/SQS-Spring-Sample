package com.electrosalaf.sqsspringsample.request;

import lombok.*;

import java.util.List;

/**
 * @author Ibrahim Lawal
 * @createdOn 17/March/2024
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwopSMSRequest {
    private List<SwopSMSBody> sms;
}

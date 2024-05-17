package com.electrosalaf.sqsspringsample.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @author Ibrahim Lawal
 * @createdOn 15/March/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SMSResponse {
    private String status;
    private String total;
    private String accepted;
    private String rejected;
    private String description;

}

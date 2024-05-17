package com.electrosalaf.sqsspringsample.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ibrahim Lawal
 * @createdOn 15/March/2024
 */


@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwopSMSBody {
    private String id;
    private String receiver;
    private String sender;
    private String message;
    private String type;

}

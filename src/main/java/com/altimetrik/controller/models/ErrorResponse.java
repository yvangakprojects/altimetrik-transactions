package com.altimetrik.controller.models;

import com.altimetrik.controller.enums.ErrorResponseCodeEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse {
    private ErrorResponseCodeEnum code;
    private String errorMessage;
    private Long timeStamp;
}

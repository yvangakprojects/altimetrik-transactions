package com.altimetrik.controller.models;

import com.altimetrik.controller.enums.ResponseTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    private ResponseTypeEnum status;
    private ErrorResponse errorResponse;
}

package com.altimetrik.controller.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorResponseCodeEnum {

    INTERNAL_SERVER_ERROR(5000),
    NOT_FOUND(4004),
    NO_CONTENT(2004);

    private int codeIntValue;

    ErrorResponseCodeEnum(int codeIntValue){
        this.codeIntValue = codeIntValue;
    }

    @JsonValue
    public int getValue(){
        return this.codeIntValue;
    }
}

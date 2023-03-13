package com.altimetrik.controller.models;


import com.altimetrik.controller.enums.ResponseTypeEnum;
import com.altimetrik.service.domain.TransactionsStats;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse extends  BaseResponse{
    private TransactionsStats transactionsStats;

    public TransactionResponse( TransactionsStats transactionsStats){
        super(ResponseTypeEnum.SUCCESS, null);
        this.transactionsStats = transactionsStats;
    }

    public TransactionResponse(ErrorResponse errorResponse){
        super(ResponseTypeEnum.FAILED, errorResponse);
    }

}

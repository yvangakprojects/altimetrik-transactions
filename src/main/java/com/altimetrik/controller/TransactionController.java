package com.altimetrik.controller;

import com.altimetrik.controller.enums.ErrorResponseCodeEnum;
import com.altimetrik.controller.models.BaseResponse;
import com.altimetrik.controller.models.ErrorResponse;
import com.altimetrik.controller.models.TransactionRequest;
import com.altimetrik.controller.models.TransactionResponse;
import com.altimetrik.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/platform/v1/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> saveTransaction(
            @RequestHeader(value = "api-version") String apiVersion,
            @RequestHeader(value = "application-Id") String applicationId,
            @RequestBody TransactionRequest request) {

        BaseResponse response = null;
        HttpStatus httpStatus = null;

        Boolean isSaved = transactionService.saveTransaction(request);

        if(Boolean.FALSE.equals(isSaved)){

           httpStatus = HttpStatus.NO_CONTENT;

           ErrorResponse errorResponse = ErrorResponse.builder()
                   .code(ErrorResponseCodeEnum.NO_CONTENT)
                   .timeStamp(new Date().getTime())
                   .errorMessage("No Content Provided")
                   .build();

             response = new TransactionResponse(errorResponse);
        }else {
            httpStatus = HttpStatus.CREATED;
            response = TransactionResponse.builder()
                    .build();
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}

package com.altimetrik.service;

import com.altimetrik.controller.models.TransactionRequest;
import com.altimetrik.service.domain.TransactionsStats;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

@Service
public class TransactionService {
    public static Stack<TransactionRequest> listOfTransactions =
            new Stack<>();

    public Boolean saveTransaction(TransactionRequest request) {
        Boolean isValidTransaction = isValid(request);
        if (isValidTransaction) {
            listOfTransactions.push(request);
        }
        return isValidTransaction;
    }

    public TransactionsStats getStats() {
        TransactionsStats transactionsStats = new TransactionsStats();
        for (TransactionRequest request : listOfTransactions) {
            if (isValid(listOfTransactions.pop())) {
                transactionsStats.setSum(addSum(transactionsStats.getSum(), request.getAmount()));
                transactionsStats.setCount(countTransactions(transactionsStats.getCount()));
            }
        }
        return transactionsStats;
    }

    private Long countTransactions(Long count) {
        return (count == null) ? 1 : count + 1;
    }

    private Double addSum(Double existingSum, Double amount) {
        return (existingSum == null) ? amount : existingSum + amount;
    }

    private Boolean isValid(TransactionRequest request) {
        Long durationInMinutes = Duration.between(request.getTime(), LocalDateTime.now())
                .toMinutes();
        Boolean isValidTransaction = (durationInMinutes > 1) ? Boolean.FALSE : Boolean.TRUE;
        return isValidTransaction;
    }

}

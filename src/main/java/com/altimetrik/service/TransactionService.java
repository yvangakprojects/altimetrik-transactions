package com.altimetrik.service;

import com.altimetrik.controller.models.TransactionRequest;
import com.altimetrik.service.domain.TransactionsStats;
import com.altimetrik.util.MaxTransactionComparator;
import com.altimetrik.util.MinTransactionComparator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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

        List<TransactionRequest> filteredTransactions = listOfTransactions.stream()
                .filter(transactionRequest -> isValid(transactionRequest))
                .collect(Collectors.toList());

        filteredTransactions.stream().collect(Collectors.counting());

        transactionsStats.setCount((long) filteredTransactions.size());
        transactionsStats.setSum(filteredTransactions.stream()
                .collect(Collectors.summingDouble(TransactionRequest::getAmount))
        );
        transactionsStats.setAvg(filteredTransactions.stream()
                .collect(Collectors.averagingDouble(TransactionRequest::getAmount))
        );
        transactionsStats.setMax(filteredTransactions.stream()
                .max(new Comparator<TransactionRequest>() {
                    @Override
                    public int compare(TransactionRequest o1, TransactionRequest o2) {
                        return (int) (o1.getAmount() - o2.getAmount());
                    }
                })
                .get()
                .getAmount()
        );
        transactionsStats.setMin(filteredTransactions.stream()
                .max(new Comparator<TransactionRequest>() {
                    @Override
                    public int compare(TransactionRequest o1, TransactionRequest o2) {
                        return (int) (o2.getAmount() - o1.getAmount());
                    }
                })
                .get()
                .getAmount()
        );
//        transactionsStats.setMax(filteredTransactions.stream()
//                .max(new MaxTransactionComparator())
//                .get()
//                .getAmount()
//        );
//        transactionsStats.setMin(filteredTransactions.stream()
//                .max(new MinTransactionComparator())
//                .get()
//                .getAmount()
//        );
        //------------------------
//        for (TransactionRequest request : listOfTransactions) {
//            if (isValid(request)) {
//                transactionsStats.setSum(addSum(transactionsStats.getSum(), request.getAmount()));
//                transactionsStats.setCount(countTransactions(transactionsStats.getCount()));
//            }
//        }
//        transactionsStats.setAvg(averageOfTransactions(transactionsStats.getSum(),
//                transactionsStats.getCount()));
        return transactionsStats;
    }

    private Double averageOfTransactions(Double sum, Long count) {
        return sum / count;
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

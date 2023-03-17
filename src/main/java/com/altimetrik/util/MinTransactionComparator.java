package com.altimetrik.util;

import com.altimetrik.controller.models.TransactionRequest;

import java.util.Comparator;

public class MinTransactionComparator implements Comparator<TransactionRequest> {
    @Override
    public int compare(TransactionRequest o1, TransactionRequest o2) {
        return (int) (o2.getAmount() - o1.getAmount());
    }
}

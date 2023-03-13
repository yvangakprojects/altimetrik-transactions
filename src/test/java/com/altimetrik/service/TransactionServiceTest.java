package com.altimetrik.service;

import com.altimetrik.controller.models.TransactionRequest;
import com.altimetrik.service.domain.TransactionsStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {

    private TransactionService subject;
    private TransactionRequest request;

    @BeforeEach
    void setUp(){
        subject = new TransactionService();
        request = TransactionRequest.builder()
                .amount(200.0)
                .time(LocalDateTime.now())
                .build();
    }

    @Nested
    @DisplayName("Saving Transactions Tests")
    class SaveTransaction{
        /**
         * POSITIVE SCENARIO
         * GIVEN A Transaction Request that occurred last 1 Minute
         * WHEN SaveTransaction
         * SHOULD return TRUE
         */
        @Test
        void whenSaveTransaction_givenTransactionThatHappenedLast1Minute_shouldVerifyTrue(){
            //GIVEN
            request = TransactionRequest.builder()
                    .amount(200.0)
                    .time(LocalDateTime.now())
                    .build();

            //WHEN
            Boolean actual = subject.saveTransaction(request);

            //ASSERTIONS
            assertTrue(actual);
            assertEquals(1,TransactionService.listOfTransactions.size());
        }

        /**
         * NEGATIVE SCENARIO
         * GIVEN A Transaction Request that occurred More than a 1 Minute
         * WHEN SaveTransaction
         * SHOULD return FALSE
         */
        @Test
        void whenSaveTransaction_givenTransactionThatHappenedMoreThan1Minute_shouldVerifyFalse(){
            //GIVEN
            request = TransactionRequest.builder()
                    .amount(200.0)
                    .time(LocalDateTime.now().minusMinutes(2))
                    .build();

            //WHEN
            Boolean actual = subject.saveTransaction(request);

            //ASSERTIONS
            assertFalse(actual);
            assertEquals(1,TransactionService.listOfTransactions.size());
        }
    }

    @Nested
    @DisplayName("Get Stats of Transactions that happened last 1 Minute")
    class GetStats{
        /**
         * POSITIVE SCENARIO
         * GIVEN a list of Transactions
         * WHEN getStats
         * SHOULD return stats of transactions that happened last 1 minute
         */
        @Test
        void givenTransactions_whenGetStats_shouldVerifyStatsOfTransactionsThatHappenedLast1Minute(){
            // WHEN
            TransactionRequest transaction_that_happened_3_minutes_ago = TransactionRequest.builder()
                    .amount(200.0)
                    .time(LocalDateTime.now().minusMinutes(3))
                    .build();
            TransactionRequest transaction_that_happened_2_minutes_ago = TransactionRequest.builder()
                    .amount(200.0)
                    .time(LocalDateTime.now().minusMinutes(2))
                    .build();
            TransactionRequest transaction_that_happened_2_seconds_ago = TransactionRequest.builder()
                    .amount(200.0)
                    .time(LocalDateTime.now().minusSeconds(2))
                    .build();
            TransactionRequest transaction_Within_minute_ago = TransactionRequest.builder()
                    .amount(200.0)
                    .time(LocalDateTime.now())
                    .build();

           // TransactionService.listOfTransactions.push(transaction_that_happened_3_minutes_ago);
           // TransactionService.listOfTransactions.push(transaction_that_happened_2_minutes_ago);
            TransactionService.listOfTransactions.push(transaction_that_happened_2_seconds_ago);
            TransactionService.listOfTransactions.push(transaction_Within_minute_ago);

            //GIVEN
            TransactionsStats actual = subject.getStats();

            //VERIFY
            assertEquals(1, actual.getCount());
        }
    }

}
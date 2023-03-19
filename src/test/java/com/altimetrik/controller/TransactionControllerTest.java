package com.altimetrik.controller;

import com.altimetrik.controller.models.TransactionRequest;
import com.altimetrik.service.TransactionService;
import com.altimetrik.service.domain.TransactionsStats;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {TransactionController.class})
@WebMvcTest(controllers = TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper;

    private String apiVersion, applicationId, path, requestBody;

    private TransactionRequest transactionRequest;
    private TransactionsStats transactionsStats;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        apiVersion = "2023-03";
        applicationId = "99957-988-987-99";
        path = "/api/platform/v1/transactions";

        transactionRequest = TransactionRequest.builder()
                .amount(200.0)
                .time(LocalDateTime.now())
                .build();

        transactionsStats = TransactionsStats.builder()
                .count(3L)
                .avg(12.5)
                .max(25.0)
                .min(5.0)
                .sum(100.0)
                .build();

        System.out.println(transactionRequest);

        objectMapper = new ObjectMapper();
        requestBody = objectMapper.writeValueAsString(transactionRequest);
    }

    @Nested
    @DisplayName("POST saving Transactions Tests")
    class SaveTransactionTests {
        /**
         * NEGATIVE SCENARIO
         * when post method to save a transaction
         * Given a transaction that happens with a time passed 1 minute
         * should return 204 No content
         */

        @Test
        void whenSaveTransaction_givenServiceReturnsFalse_shouldExpect204NoContent() throws Exception {
            // GIVEN
            doReturn(Boolean.FALSE).when(transactionService).saveTransaction(any(TransactionRequest.class));
            // WHEN
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .header("api-version", apiVersion)
                            .header("application-Id", applicationId)
                            .content(requestBody))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.status", is("FAILED")))
                    .andExpect(jsonPath("$.errorResponse.code", is(2004)))
                    .andExpect(jsonPath("$.errorResponse.errorMessage", is("No Content Provided")));
        }

        /**
         * POSITIVE SCENARIO
         * when post method to save a transaction
         * Given a transaction that happens within 1 minute
         * should return 201 Created
         */

        @Test
        void whenSaveTransaction_givenServiceReturnsTrue_shouldExpect201Created() throws Exception {
            // GIVEN
            doReturn(Boolean.TRUE).when(transactionService).saveTransaction(any(TransactionRequest.class));
            // WHEN
            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .header("api-version", apiVersion)
                            .header("application-Id", applicationId)
                            .content(requestBody))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

    }

    @Nested
    @DisplayName("GET Transactions")
    class GetTransactions{
        /**
         * POSITIVE SCENARIO
         * when GET method to pull transactions stats
         * Given transactions
         * should return 200 OK and the transaction stats
         */

        @Test
        void whenSaveTransaction_givenServiceReturnsFalse_shouldExpect204NoContent() throws Exception {
            // GIVEN
            doReturn(transactionsStats).when(transactionService).getStats();
            // WHEN
            mockMvc.perform(get(path)
                            .header("api-version", apiVersion)
                            .header("application-Id", applicationId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.transactionsStats.sum", is(100.0)));

        }
    }
}
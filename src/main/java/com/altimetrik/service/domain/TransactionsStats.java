package com.altimetrik.service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsStats {
    private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    private Long count;
}

package com.altimetrik.service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsStats {
    private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    private Long count;
}

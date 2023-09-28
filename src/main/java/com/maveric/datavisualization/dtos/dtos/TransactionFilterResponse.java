package com.maveric.datavisualization.dtos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFilterResponse {
    private Map<String, Double> filterTransactions;
}

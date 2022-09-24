package org.eexor.debtoptimizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Asset {
    private String name;
    private int returnOnInvestment;
    private int totalProfit;
}

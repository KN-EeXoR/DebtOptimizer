package org.eexor.debtoptimizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Liability {
    private String name;
    private int interestRate;
    private int monthlyPayment;
    private int compoundInterest;
}

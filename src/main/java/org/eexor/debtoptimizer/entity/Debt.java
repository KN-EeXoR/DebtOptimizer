package org.eexor.debtoptimizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DEBT")
public class Debt extends Deposit {

    @Column(name = "AMOUNT_OF_DEBT", nullable = false)
    private Double amountOfDebt;    // how much you still have to pay

    @Column(name = "MINIMUM_MONTHLY_PAYMENT", nullable = false)
    private Double minimumMonthlyPayment;   // the minimum payment requirement

    @Column(name = "REPAID_MONEY", nullable = false)
    private Double repaidMoney;

    @Column(name = "YEAR_OF_REPAYMENT")
    private Integer yearOfRepayment;

    /**
     * @param name Distinguishable name which describes the debt
     * @param amountOfDebt The amount of money you have to pay back
     * @param interest annual increase in the amount
     * @param minimumMonthlyPayment minimum monthly payment
     */
    public Debt(String name, double amountOfDebt, double interest, double minimumMonthlyPayment) {
        this.minimumMonthlyPayment = minimumMonthlyPayment;
        this.name = name;
        this.amountOfDebt = amountOfDebt;
        this.interestRate = interest;
        this.repaidMoney = 0.0;
        this.yearOfRepayment = -1;
        this.minimumMonthlyPayment = round_decimal(this.minimumMonthlyPayment);
    }
    /**
     * @param name Distinguishable name which describes the debt
     * @param amountOfDebt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public Debt(String name, double amountOfDebt, double interest) {
        this.minimumMonthlyPayment = (amountOfDebt - (amountOfDebt /(interest+1)));
        this.name = name;
        this.amountOfDebt = amountOfDebt;
        this.repaidMoney = 0.0;
        this.yearOfRepayment = -1;
        this.interestRate = interest;
        this.minimumMonthlyPayment = round_decimal(this.minimumMonthlyPayment);
    }
    /** It reduces debt
     * @param money the amount of money we can spend
     * @return the rest of the money we have left
     */
    public double pay(double money){
        this.repaidMoney += money;
        this.amountOfDebt -= money;
        if(this.amountOfDebt <0){
            double result = this.amountOfDebt * -1;
            this.amountOfDebt = 0.0;
            this.repaidMoney -= result;
            return result;
        }
        return 0;
    }
    /**
     * increase in debt
     */
    public void charge_interest(){
        this.amountOfDebt += this.amountOfDebt * this.interestRate;
        this.amountOfDebt = round_decimal(this.amountOfDebt);
    }

    @Override
    public String toString() {
        return this.name + " to pay " + this.amountOfDebt + " with interest " + this.interestRate +"% at minimum payment " +this.minimumMonthlyPayment;
    }
    public String toString_paid() {
        return this.name + " paid of " + this.yearOfRepayment + " year, paid in total " + this.repaidMoney + " with interest " + this.interestRate + "% at minimum payment " + this.minimumMonthlyPayment;
    }
}

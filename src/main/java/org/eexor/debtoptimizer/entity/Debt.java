package org.eexor.debtoptimizer.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DEBT")
public class Debt extends Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Serial
    private long id = -1;

    @Column(name = "AMOUNT", nullable = false)
    private Double debt;

    @Column(name = "MINIMUM_MONTHLY_PAY", nullable = false)
    private Double min_pay;

    private double paid;
    private int year;

    /**
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     * @param min_paymanet minimum monthly payment
     */
    public Debt(String name, double debt, double interest, double min_paymanet) {
        this.min_pay = min_paymanet;
        this.name = name;
        this.debt = debt;
        this.interest = interest;
        this.paid = 0;
        this.year = -1;
        this.min_pay = round_decimal(this.min_pay);
    }
    /**
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public Debt(String name, double debt, double interest) {
        this.min_pay = (debt - (debt /(interest+1)));
        this.name = name;
        this.debt = debt;
        this.paid = 0;
        this.year = -1;
        this.interest = interest;
        this.min_pay = round_decimal(this.min_pay);
    }
    /** It reduces debt
     * @param money the amount of money we can spend
     * @return the rest of the money we have left
     */
    public double pay(double money){
        this.paid += money;
        this.debt -= money;
        if(this.debt <0){
            double wynik = this.debt * -1;
            this.debt = 0;
            this.paid -= wynik;
            return wynik;
        }
        return 0;
    }
    /**
     * increase in debt
     */
    public void charge_interest(){
        this.debt += this.debt * this.interest;
        this.debt = round_decimal(this.debt);
    };
    /** Returns how much you still have to pay
     * @return
     */
    public double to_be_repaid(){
        return this.debt;
    }
    /** Returns the minimum payment requirement
     * @return
     */
    public double min_paymanet(){
        return this.min_pay;
    }
    public void year_of_repayment(int year){
        this.year = year;
    }
    public double get_repaid_money(){
        return this.paid;
    }
    public int get_repayment_year() {
        return this.year;
    }
    @Override
    public String toString() {
        return this.name + " to pay " + this.debt + " with interest " + this.interest +"% at minimum payment " +this.min_pay;
    }
    public String toString_paid() {
        return this.name + " paid of " + this.year + " year, paid in total " + this.paid + " with interest " + this.interest + "% at minimum payment " + this.min_pay;
    }
}

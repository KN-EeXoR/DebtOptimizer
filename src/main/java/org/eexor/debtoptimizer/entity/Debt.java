package org.eexor.debtoptimizer.entity;


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
public class Debt implements Comparable<Debt> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Serial
    private long id = -1;

    @Column(name = "DESCRIPTION", length = 50, nullable = false)
    private String name;
    @Column(name = "AMOUNT", nullable = false)
    private Double debt;

    @Column(name = "INTEREST", nullable = false)
    private Double interest;

    @Column(name = "MINIMUM_MONTHLY_PAY", nullable = false)
    private Double min_pay;

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
        this.min_pay = round_decimal(this.min_pay);
    }
    /**
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public Debt(String name, double debt, double interest) {
        this.min_pay = (debt - (debt /(interest+1)))/12;
        this.name = name;
        this.debt = debt;
        this.interest = interest;
        this.min_pay = round_decimal(this.min_pay);
    }
    /** It reduces debt
     * @param money the amount of money we can spend
     * @return the rest of the money we have left
     */
    public double pay_back(double money){
        this.debt -= money;
        if(this.debt <0){
            double wynik = this.debt * -1;
            this.debt = Double.valueOf(0);
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
    /** Returns the increment of debt
     * @return
     */
    public double interest(){
        return this.interest;
    }
    /** Returns the name of the debt
     * @return 
     */
    public String name(){
        return this.name;
    }
    @Override
    public int compareTo(Debt o) {
        if(this.interest > o.interest)
            return -1;
        else if(this.interest == o.interest)
            return 0;
        else return 1;
    }
    @Override
    public boolean equals(Object obj) {
        
        return this.name == ((Debt)obj).name;
    }
    @Override
    public String toString() {
        return this.name + " to pay " + this.debt + " with interest " + this.interest +"% at minimum payment " +this.min_pay;
    }
    private double round_decimal(double number){
        return (double)Math.round(number*100)/100;
    }
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}

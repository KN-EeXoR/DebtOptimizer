package org.eexor.debtoptimizer.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Debt extends Deposit  {
    
    private double debt;
    private double min_pay;

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
    public double pay(double money){
        this.debt -= money;
        if(this.debt <0){
            double wynik = this.debt * -1;
            this.debt = 0;
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
    
    @Override
    public String toString() {
        return this.name + " to pay " + this.debt + " with interest " + this.interest +"% at minimum payment " +this.min_pay;
    }
    
}

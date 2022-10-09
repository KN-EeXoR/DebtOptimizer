package org.eexor.debtoptimizer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "INVESTMENT")
public class Investment extends Deposit {

    @Column(name = "TRANSFERRED")
    private Double transferred;

    @Column(name = "EARNED")
    private Double earned;

    @Column(name = "MAX_PAY")
    private Double max_pay;

    public Investment(String name, double interest, double max_pay) {
        this.name = name;
        this.interestRate = interest;
        this.max_pay  = max_pay;
        transferred = 0.0;
    }
    /** It reduces debt
     * @param money the amount of money we can spend
     * @return the rest of the money we have left
     */
    public double pay(double money){
        this.transferred += money;
        if(this.transferred > this.max_pay){
            double result = this.transferred -this.max_pay;
            this.transferred = this.max_pay;
            return result;
        }
        return 0;
    }
    public void charge_interest(){
        transferred *=(this.interestRate +1);
        transferred = round_decimal(transferred);
    }
    public double earned(){
        return this.earned;
    }
    public double payout(){
        double money = this.transferred;
        this.earned += this.transferred;
        this.transferred = 0.0;
        return money;
    }
    @Override
    public String toString() {
        return this.name + " earned " + this.earned + " with interest " + this.interestRate +"% with maxim at " +this.max_pay;
    }
    
    
}


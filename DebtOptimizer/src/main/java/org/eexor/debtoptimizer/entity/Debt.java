package org.eexor.debtoptimizer.entity;


import lombok.Data;

@Data
public class Debt implements Comparable<Debt> {
    private String name;
    private double debt;
    private double interest;
    private double min_pay;

    Debt(String name, double debt, double interest, double min_paymanet) {
        this.min_pay = min_paymanet;
        this.name = name;
        this.debt = debt;
        this.interest = interest;
        this.min_pay = round_decimal(this.min_pay);
    }
    Debt(String name, double debt, double interest) {
        this.min_pay = (debt - (debt /(interest+1)))/12;
        this.name = name;
        this.debt = debt;
        this.interest = interest;
        this.min_pay = round_decimal(this.min_pay);
    }
    public double pay_back(double money){
        this.debt -= money;
        if(this.debt <0){
            double wynik = this.debt * -1;
            this.debt = 0;
            return wynik;
        }
        return 0;
    }
    public void charge_interest(){
        this.debt += this.debt * this.interest;
        this.debt = round_decimal(this.debt);
    };
    public double to_be_repaid(){
        return this.debt;
    }
    public double min_paymanet(){
        return this.min_pay;
    }
    public double interest(){
        return this.interest;
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

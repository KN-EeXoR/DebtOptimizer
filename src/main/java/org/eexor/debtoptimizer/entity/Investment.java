package org.eexor.debtoptimizer.entity;

public class Investment extends Deposit {
    private double transferred;
    private double earned;
    private double max_pay;

    public Investment(String name, double interest, double max_pay) {
        this.name = name;
        this.interest = interest;
        this.max_pay  = max_pay;
        transferred = 0;
    }
    /** It reduces debt
     * @param money the amount of money we can spend
     * @return the rest of the money we have left
     */
    public double pay(double money){
        this.transferred += money;
        if(this.transferred >this.max_pay){
            double wynik = this.transferred -this.max_pay;
            this.transferred = this.max_pay;
            return wynik;
        }
        return 0;
    }
    public double max_pay(){
        return this.max_pay;
    }
    public void charge_interest(){
        transferred *=(this.interest+1);
        transferred = round_decimal(transferred);
    }
    public double what_transferred(){
        return this.transferred;
    }
    public double earned(){
        return this.earned;
    }
    public double payout(){
        double money = this.transferred;
        this.earned += this.transferred;
        this.transferred = 0;
        return money;
    }
    @Override
    public String toString() {
        return this.name + " earned " + this.earned + " with interest " + this.interest +"% with maxim at " +this.max_pay;
    }
    
    
}


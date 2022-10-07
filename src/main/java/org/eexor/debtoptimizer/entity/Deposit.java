package org.eexor.debtoptimizer.entity;

public abstract class Deposit implements Comparable<Deposit> {
    protected int id = -1;
    protected String name;
    protected double interest;

    protected double round_decimal(double number){
        return (double)Math.round(number*100)/100;
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
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    abstract public double pay(double money);
    abstract public void charge_interest();
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        
        return this.name == ((Deposit)obj).name;
    }
    
    @Override
    public int compareTo(Deposit o) {
        return Double.compare(o.interest, this.interest);
    }
    @Override
    abstract public String toString();
}

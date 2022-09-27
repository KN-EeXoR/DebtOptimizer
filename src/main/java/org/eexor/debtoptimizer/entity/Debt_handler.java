package org.eexor.debtoptimizer.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class Debt_handler {

    private List<Deposit> list_of_debt;
    private double income;
    private double my_account;
    private List<Debt> list_of_paid_debt;
    /**
     * @param income How much money we can spend evry month to pay off debts
     */
    Debt_handler(double income){
        list_of_debt = new ArrayList<>();
        list_of_paid_debt = new ArrayList<>();
        this.income = income;
        my_account = 0;
    }
    /** Adding debt to your list of debts
     * @param your_debt object of class Debt
     */
    public void add_debt(Debt your_debt){
        int index = Collections.binarySearch(list_of_debt, your_debt);
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_debt.add(index, your_debt);
    }
    public void add_investment(Investment inv){
        int index = Collections.binarySearch(list_of_debt, inv );
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_debt.add(index, inv);
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     * @param min_paymanet minimum monthly payment
     */
    public void add_debt(String name, double debt, double interest, double min_paymanet){
        Debt your_debt = new Debt(name, debt, interest, min_paymanet);
        int index = Collections.binarySearch(list_of_debt, your_debt);
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_debt.add(index, your_debt);
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public void add_debt(String name, double debt, double interest){
        Debt your_debt = new Debt(name, debt, interest);
        int index = Collections.binarySearch(list_of_debt, your_debt);
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_debt.add(index, your_debt);
    }
    /** Return amount of unpaid debts
     * @return 
     */
    public int nuber_of_debts(){
        return list_of_debt.size();
    }
    /** Changes the current monthly income to the given one
     * @param income 
     */
    public void change_income(double income){
        this.income = income;
    }
    /** Return String which contains information about debt at given index
     * @param index 
     * @return
     */
    public String get_debt_info(int index){
        return list_of_debt.get(index).toString();
    }
    /**
     * It reduces your debts by a monthly increase in an optimal way and calculates the receipts if the debts have been repaid
     */
    public void evaluate_income(){
        double money = income + my_account;
        for (Deposit debt : list_of_debt) {
            if (debt instanceof Debt){
                debt.pay(((Debt)debt).min_paymanet());
                money -= ((Debt)debt).min_paymanet();
            }
            
        }
        if (money < 0){
            System.out.println("Not enough money to pay minimal payment");
            return;
        }
        for (int i = 0; i < list_of_debt.size(); i++) {
            money = list_of_debt.get(i).pay(money);
            if( list_of_debt.get(i) instanceof Debt && ((Debt) list_of_debt.get(i)).to_be_repaid() == 0){
                list_of_paid_debt.add((Debt) list_of_debt.get(i));
                list_of_debt.remove(i);
                i--;
            }
            if(money == 0)
                break;
        }
        my_account = money;
    }
    /**
     * Calculates the increase in our debts
     */
    public void charge_interest(){
        for (Deposit debt : list_of_debt) {
            debt.charge_interest();
        }
    }
    public void payout_investment(){
        for (Deposit deposit : list_of_debt) {
            if(deposit instanceof Investment){
                my_account += ((Investment)deposit).payout();
            }
        }
    }
    /**
     * Prints a String which contains information about unpaid debts
     */
    public void print_all_debts_info(){
        for (Deposit debt : list_of_debt) {
            System.out.println(debt.toString());
        }
    }
    public class DepInfo{
        public String name;
        public double[] numbers = new double[3];
        public boolean Debt_T_Inves_F;
        @Override
        public String toString() {
            if(Debt_T_Inves_F){
                return "Debt " + this.name + " to pay " + this.numbers[0] + " with interest " + this.numbers[1] +"% at minimum payment " +this.numbers[2];
            }else{
                return "Investment " +this.name + " transferred " + this.numbers[0] + " with interest " + this.numbers[1] +"% with maxim at " +this.numbers[2];
            }
        }
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a two-dimensional array where one row corresponds to one debt, each row has three columns with the respective: amount of debt, increase, minimum payment 
     */
    public DepInfo[] get_all_debts_info(){
        int size = this.list_of_debt.size();
        DepInfo data[] = new DepInfo[size];
        Debt debt;
        Investment inv;
        for (int i =0 ; i<size; i++) {
            data[i] =  new DepInfo();
            if(list_of_debt.get(i)instanceof Debt){
                debt = (Debt)list_of_debt.get(i);
                data[i].name = debt.name();
                data[i].numbers[0] =  debt.to_be_repaid();
                data[i].numbers[1] = debt.interest();
                data[i].numbers[2] = debt.min_paymanet();
                data[i].Debt_T_Inves_F = true;
            }else{
                inv = (Investment)list_of_debt.get(i);
                data[i].name = inv.name();
                data[i].numbers[0] =  inv.what_transferred();
                data[i].numbers[1] = inv.interest();
                data[i].numbers[2] = inv.max_pay();
                data[i].Debt_T_Inves_F = false;
            }
           
        }
        return data;
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a array with names of debst
     */
    public String[] get_table_with_all_debts_name_info(){
        int size = this.list_of_debt.size();
        String data[] = new String[size];
        for (int i =0 ; i<size; i++) {
            data[i] = this.list_of_debt.get(i).name();
        }
        return data;
    }
    /** calculates our account balance after years
     * @param years the number of years to elapse
     * @return our account balance after these years
     */
    public double after_years(int years){
        for (int i = 0; i < years; i++) {
            for (int j = 0; j < 12; j++) {
                evaluate_income();
            }
            charge_interest();
            payout_investment();
        }
        double result = 0;
        for (Deposit debt : list_of_debt) {
            if (debt instanceof Debt){
                result += ((Debt)debt).to_be_repaid();
            }
        }
        return my_account - result;
    }

}



package org.eexor.debtoptimizer.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.Data;
import org.eexor.debtoptimizer.entity.Debt;
import org.eexor.debtoptimizer.entity.Deposit;
import org.eexor.debtoptimizer.entity.Investment;

@Data
public class Deposit_handler {

    private List<Deposit> list_of_deposit;
    private double income;
    private double my_account;
    private List<Debt> list_of_paid_debt;
    private int nuber_of_debts;
    private int year = 0;
    /**
     * @param income How much money we can spend evry month to pay off debts
     */
    Deposit_handler(double income){
        list_of_deposit = new ArrayList<>();
        list_of_paid_debt = new ArrayList<>();
        this.income = income;
        my_account = 0;
        year = 0;
    }
    /** Adding debt to your list of debts
     * @param your_debt object of class Debt
     */
    public void add_debt(Debt your_debt){
        int index = Collections.binarySearch(list_of_deposit, your_debt);
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_deposit.add(index, your_debt);
        this.nuber_of_debts++;
    }
    public void add_investment(Investment inv){
        int index = Collections.binarySearch(list_of_deposit, inv );
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_deposit.add(index, inv);
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     * @param min_paymanet minimum monthly payment
     */
    public void add_debt(String name, double debt, double interest, double min_paymanet){
        Debt your_debt = new Debt(name, debt, interest, min_paymanet);
        int index = Collections.binarySearch(list_of_deposit, your_debt);
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_deposit.add(index, your_debt);
        this.nuber_of_debts++;
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public void add_debt(String name, double debt, double interest){
        Debt your_debt = new Debt(name, debt, interest);
        int index = Collections.binarySearch(list_of_deposit, your_debt);
        if (index<0){
            index = index*(-1) -1;
        }
        list_of_deposit.add(index, your_debt);
        this.nuber_of_debts++;
    }
    /** Return amount of unpaid debts
     * @return 
     */
    public int number_of_deposit(){
        return list_of_deposit.size();
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
        return list_of_deposit.get(index).toString();
    }
    /**
     * It reduces your debts by a monthly increase in an optimal way and calculates the receipts if the debts have been repaid
     */
    public int evaluate_income(int year){
        double money = income + my_account;
        for (Deposit debt : list_of_deposit) {
            if (debt instanceof Debt){
                debt.pay(((Debt)debt).getMinimumMonthlyPayment());
                money -= ((Debt)debt).getMinimumMonthlyPayment();
            }
            
        }
        if (money < 0){
            System.out.println("Not enough money to pay minimal payment");
            return - 1;
        }
        int paid = 0;
        for (int i = 0; i < list_of_deposit.size(); i++) {
            money = list_of_deposit.get(i).pay(money);
            if( list_of_deposit.get(i) instanceof Debt && ((Debt) list_of_deposit.get(i)).getAmountOfDebt() == 0){
                ((Debt) list_of_deposit.get(i)).setYearOfRepayment(year);
                list_of_paid_debt.add((Debt) list_of_deposit.get(i));
                list_of_deposit.remove(i);
                this.nuber_of_debts--;
                paid++;
                i--;
            }
            if(money == 0)
                break;
        }
        my_account = money;
        return paid;
    }
    /**
     * Calculates the increase in our debts
     */
    public void charge_interest(){
        for (Deposit debt : list_of_deposit) {
            debt.charge_interest();
        }
    }
    public void payout_investment(){
        for (Deposit deposit : list_of_deposit) {
            if(deposit instanceof Investment){
                my_account += ((Investment)deposit).payout();
            }
        }
    }
    /**
     * Prints a String which contains information about unpaid debts
     */
    public void print_all_debts_info(){
        for (Deposit debt : list_of_deposit) {
            System.out.println(debt.toString());
        }
    }
    public class DepInfo{
        public String name;
        public double[] numbers = new double[3];
        public boolean Debt_True_Inves_False;
        @Override
        public String toString() {
            if(Debt_True_Inves_False){
                return "Debt " + this.name + " to pay " + this.numbers[0] + " with interest " + this.numbers[1] +"% at minimum payment " +this.numbers[2];
            }else{
                return "Investment " +this.name + " income " + this.numbers[0] + " with interest " + this.numbers[1] +"% with maxim at " +this.numbers[2];
            }
        }
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a two-dimensional array where one row corresponds to one debt, each row has three columns with the respective: amount of debt, increase, minimum payment 
     */
    

    public class current_info{
        public Debt remaining_debt[];
        public Investment investment[]; 
        public Debt paidoff[];
       
    }
    public current_info get_current_info(){
        current_info info = new current_info();
        info.remaining_debt = new Debt[nuber_of_debts];
        info.investment = new Investment[number_of_deposit()-nuber_of_debts];
        int i =0 , j =0;
        for (Deposit deposit : list_of_deposit) {
            if(deposit instanceof Debt){
                info.remaining_debt[i] = (Debt) deposit;
                i++;
            }else{
                info.investment[j] = (Investment) deposit;
                j++;
            }
        }
        info.paidoff =  list_of_paid_debt.toArray(new Debt[0]);
        return info;
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a array with names of debst
     */
    
    public double in_debt(){
        double result = 0;
        for (Deposit debt : list_of_deposit) {
            if (debt instanceof Debt){
                result += ((Debt)debt).getAmountOfDebt();
            }
        }
        return result;
    }
    public void paid_debt_info(){

    }
    /** calculates our account balance after years
     * @param years the number of years to elapse
     * @return our account balance after these years
     */
    public void after_years(int years){
        years += year;
        int paid;
        int i = 0;
        int index;
        print_all_debts_info();
        System.out.println();
        while(nuber_of_debts>0 && year<years){
            paid = evaluate_income(year);
            charge_interest();
            payout_investment();
            year++;
            for (i = 0; i < paid; i++) {
                index = list_of_paid_debt.size() - 1;
                System.out.println(list_of_paid_debt.get(index).toString_paid());
                index--;
            }
            if (i>0){
                i = 0;
                print_all_debts_info();
                System.out.println();
            }
             
            
        }
        while(year<years){
            evaluate_income(year);
            charge_interest();
            payout_investment();
            year++;
        } 
        print_all_debts_info();
        System.out.println("you end with "+ (my_account - in_debt())+ " money after " + year + " years");
    }
    public current_info pay_next_debt(){
        int paid = 0;
        while(nuber_of_debts>0 && paid == 0){
            paid = evaluate_income(year);
            charge_interest();
            payout_investment();
            year++;
             
            
        }
        return get_current_info();
    }
    public JsonElement to_JSON(current_info info){
        return new Gson().toJsonTree(info);
    }

}



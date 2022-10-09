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
public class Deposithandler {

    private List<Deposit> listofdeposit;
    private double income;
    private double myaccount;
    private List<Debt> listofpaiddebt;
    private int nuberofdebts;
    private int year = 0;
    /**
     * @param income How much money we can spend evry month to pay off debts
     */
    Deposithandler(double income){
        listofdeposit = new ArrayList<>();
        listofpaiddebt = new ArrayList<>();
        this.income = income;
        myaccount = 0;
        year = 0;
    }
    /** Adding debt to your list of debts
     * @param yourdebt object of class Debt
     */
    public void adddebt(Debt yourdebt){
        int index = Collections.binarySearch(listofdeposit, yourdebt);
        if (index<0){
            index = index*(-1) -1;
        }
        listofdeposit.add(index, yourdebt);
        this.nuberofdebts++;
    }
    public void addinvestment(Investment inv){
        int index = Collections.binarySearch(listofdeposit, inv );
        if (index<0){
            index = index*(-1) -1;
        }
        listofdeposit.add(index, inv);
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     * @param minpaymanet minimum monthly payment
     */
    public void adddebt(String name, double debt, double interest, double minpaymanet){
        Debt yourdebt = new Debt(name, debt, interest, minpaymanet);
        int index = Collections.binarySearch(listofdeposit, yourdebt);
        if (index<0){
            index = index*(-1) -1;
        }
        listofdeposit.add(index, yourdebt);
        this.nuberofdebts++;
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public void adddebt(String name, double debt, double interest){
        Debt yourdebt = new Debt(name, debt, interest);
        int index = Collections.binarySearch(listofdeposit, yourdebt);
        if (index<0){
            index = index*(-1) -1;
        }
        listofdeposit.add(index, yourdebt);
        this.nuberofdebts++;
    }
    /** Return amount of unpaid debts
     * @return 
     */
    private int numberofdeposit(){
        return listofdeposit.size();
    }
    /** Changes the current monthly income to the given one
     * @param income 
     */
    private void changeincome(double income){
        this.income = income;
    }
    /** Return String which contains information about debt at given index
     * @param index 
     * @return
     */
    private String getdebtinfo(int index){
        return listofdeposit.get(index).toString();
    }
    /**
     * It reduces your debts by a monthly increase in an optimal way and calculates the receipts if the debts have been repaid
     */
    private int evaluateincome(int year){
        double money = income + myaccount;
        for (Deposit debt : listofdeposit) {
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
        for (int i = 0; i < listofdeposit.size(); i++) {
            money = listofdeposit.get(i).pay(money);
            if( listofdeposit.get(i) instanceof Debt && ((Debt) listofdeposit.get(i)).getAmountOfDebt() == 0){
                ((Debt) listofdeposit.get(i)).setYearOfRepayment(year);
                listofpaiddebt.add((Debt) listofdeposit.get(i));
                listofdeposit.remove(i);
                this.nuberofdebts--;
                paid++;
                i--;
            }
            if(money == 0)
                break;
        }
        myaccount = money;
        return paid;
    }
    /**
     * Calculates the increase in our debts
     */
    private void chargeinterest(){
        for (Deposit debt : listofdeposit) {
            debt.chargeinterest();
        }
    }
    private void payoutinvestment(){
        for (Deposit deposit : listofdeposit) {
            if(deposit instanceof Investment){
                myaccount += ((Investment)deposit).payout();
            }
        }
    }
    /**
     * Prints a String which contains information about unpaid debts
     */
    private void printalldebtsinfo(){
        for (Deposit debt : listofdeposit) {
            System.out.println(debt.toString());
        }
    }
    private class DepInfo{
        public String name;
        public double[] numbers = new double[3];
        public boolean DebtTrueInvesFalse;
        @Override
        public String toString() {
            if(DebtTrueInvesFalse){
                return "Debt " + this.name + " to pay " + this.numbers[0] + " with interest " + this.numbers[1] +"% at minimum payment " +this.numbers[2];
            }else{
                return "Investment " +this.name + " income " + this.numbers[0] + " with interest " + this.numbers[1] +"% with maxim at " +this.numbers[2];
            }
        }
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a two-dimensional array where one row corresponds to one debt, each row has three columns with the respective: amount of debt, increase, minimum payment 
     */
    

    public class currentinfo{
        public Debt remainingdebt[];
        public Investment investment[]; 
        public Debt paidoff[];
       
    }
    private currentinfo getcurrentinfo(){
        currentinfo info = new currentinfo();
        info.remainingdebt = new Debt[nuberofdebts];
        info.investment = new Investment[numberofdeposit()-nuberofdebts];
        int i =0 , j =0;
        for (Deposit deposit : listofdeposit) {
            if(deposit instanceof Debt){
                info.remainingdebt[i] = (Debt) deposit;
                i++;
            }else{
                info.investment[j] = (Investment) deposit;
                j++;
            }
        }
        info.paidoff =  listofpaiddebt.toArray(new Debt[0]);
        return info;
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a array with names of debst
     */
    
    private double indebt(){
        double result = 0;
        for (Deposit debt : listofdeposit) {
            if (debt instanceof Debt){
                result += ((Debt)debt).getAmountOfDebt();
            }
        }
        return result;
    }
    /** calculates our account balance after years
     * @param years the number of years to elapse
     * @return our account balance after these years
     */
    private void afteryears(int years){
        years += year;
        int paid;
        int i = 0;
        int index;
        printalldebtsinfo();
        System.out.println();
        while(nuberofdebts>0 && year<years){
            paid = evaluateincome(year);
            chargeinterest();
            payoutinvestment();
            year++;
            for (i = 0; i < paid; i++) {
                index = listofpaiddebt.size() - 1;
                System.out.println(listofpaiddebt.get(index).toStringpaid());
                index--;
            }
            if (i>0){
                i = 0;
                printalldebtsinfo();
                System.out.println();
            }
             
            
        }
        while(year<years){
            evaluateincome(year);
            chargeinterest();
            payoutinvestment();
            year++;
        } 
        printalldebtsinfo();
        System.out.println("you end with "+ (myaccount - indebt())+ " money after " + year + " years");
    }
    public currentinfo paynextdebt(){
        int paid = 0;
        while(nuberofdebts>0 && paid == 0){
            paid = evaluateincome(year);
            chargeinterest();
            payoutinvestment();
            year++;
             
            
        }
        return getcurrentinfo();
    }
    private JsonElement toJSON(currentinfo info){
        return new Gson().toJsonTree(info);
    }

}



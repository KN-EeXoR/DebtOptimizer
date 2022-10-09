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
import org.eexor.debtoptimizer.repositories.DebtRepository;
import org.eexor.debtoptimizer.repositories.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class DepositHandler {

    private DebtRepository debtRepository;
    private InvestmentRepository investmentRepository;

    private List<Deposit> listOfDeposit;
    private double income = 0;  // How much money we can spend every month to pay off debts
    private double myAccount = 0;
    private List<Debt> listOfPaidDebt;
    private int numberOfDebts = 0;
    private int year = 0;

    @Autowired
    public DepositHandler(DebtRepository debtRepository, InvestmentRepository investmentRepository) {
        this.debtRepository = debtRepository;
        this.investmentRepository = investmentRepository;
        listOfDeposit = new ArrayList<>();
        listOfPaidDebt = new ArrayList<>();
        myAccount = 0;
        year = 0;
    }
    /** Adding debt to your list of debts
     * @param yourDebt object of class Debt
     */

    public void addDebt(Debt yourDebt){
        int index = Collections.binarySearch(listOfDeposit, yourDebt);
        if (index<0){
            index = index*(-1) -1;
        }
        listOfDeposit.add(index, yourDebt);
        this.numberOfDebts++;
    }
    public void addInvestment(Investment inv){
        int index = Collections.binarySearch(listOfDeposit, inv );
        if (index<0){
            index = index*(-1) -1;
        }
        listOfDeposit.add(index, inv);
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     * @param minimumMonthlyPayment minimum monthly payment
     */
    public void addDebt(String name, double debt, double interest, double minimumMonthlyPayment){
        Debt yourDebt = new Debt(name, debt, interest, minimumMonthlyPayment);
        int index = Collections.binarySearch(listOfDeposit, yourDebt);
        if (index<0){
            index = index*(-1) -1;
        }
        listOfDeposit.add(index, yourDebt);
        this.numberOfDebts++;
    }

    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public void addDebt(String name, double debt, double interest){
        Debt yourDebt = new Debt(name, debt, interest);
        int index = Collections.binarySearch(listOfDeposit, yourDebt);
        if (index<0){
            index = index*(-1) -1;
        }
        listOfDeposit.add(index, yourDebt);
        this.numberOfDebts++;
    }

    /** Return amount of unpaid debts
     * @return 
     */
    private int numberOfDeposit(){
        return listOfDeposit.size();
    }

    /** Changes the current monthly income to the given one
     * @param income 
     */
    private void changeIncome(double income){
        this.income = income;
    }

    /** Return String which contains information about debt at given index
     * @param index 
     * @return
     */
    private String getDebtInfo(int index){
        return listOfDeposit.get(index).toString();
    }

    /**
     * It reduces your debts by a monthly increase in an optimal way and calculates the receipts if the debts have been repaid
     */
    private int evaluateIncome(int year){
        double money = income + myAccount;
        for (Deposit debt : listOfDeposit) {
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
        for (int i = 0; i < listOfDeposit.size(); i++) {
            money = listOfDeposit.get(i).pay(money);
            if( listOfDeposit.get(i) instanceof Debt && ((Debt) listOfDeposit.get(i)).getAmountOfDebt() == 0){
                ((Debt) listOfDeposit.get(i)).setYearOfRepayment(year);
                listOfPaidDebt.add((Debt) listOfDeposit.get(i));
                listOfDeposit.remove(i);
                this.numberOfDebts--;
                paid++;
                i--;
            }
            if(money == 0)
                break;
        }
        myAccount = money;
        return paid;
    }

    /**
     * Calculates the increase in our debts
     */
    private void chargeInterest(){
        for (Deposit debt : listOfDeposit) {
            debt.chargeInterest();
        }
    }

    private void payOutInvestment(){
        for (Deposit deposit : listOfDeposit) {
            if(deposit instanceof Investment){
                myAccount += ((Investment)deposit).payOut();
            }
        }
    }

    /**
     * Prints a String which contains information about unpaid debts
     */
    private void printAllDebtsInfo(){
        for (Deposit debt : listOfDeposit) {
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
    private CurrentInfo getCurrentInfo(){
        CurrentInfo info = new CurrentInfo();
        info.remainingDebt = new Debt[numberOfDebts];
        info.investment = new Investment[numberOfDeposit()-numberOfDebts];
        int i =0 , j =0;
        for (Deposit deposit : listOfDeposit) {
            if(deposit instanceof Debt){
                info.remainingDebt[i] = (Debt) deposit;
                i++;
            }else{
                info.investment[j] = (Investment) deposit;
                j++;
            }
        }
        info.paidOff =  listOfPaidDebt.toArray(new Debt[0]);
        return info;
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a array with names of debst
     */
    
    private double inDebt(){
        double result = 0;
        for (Deposit debt : listOfDeposit) {
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
    private void afterYears(int years){
        years += year;
        int paid;
        int i = 0;
        int index;
        printAllDebtsInfo();
        System.out.println();

        while(numberOfDebts>0 && year<years){
            paid = evaluateIncome(year);
            chargeInterest();
            payOutInvestment();
            year++;
            for (i = 0; i < paid; i++) {
                index = listOfPaidDebt.size() - 1;
                System.out.println(listOfPaidDebt.get(index).toStringPaid());
                index--;
            }
            if (i>0){
                i = 0;
                printAllDebtsInfo();
                System.out.println();
            }
        }
        while(year<years){
            evaluateIncome(year);
            chargeInterest();
            payOutInvestment();
            year++;
        } 

        printAllDebtsInfo();
        System.out.println("you end with "+ (myAccount - inDebt())+ " money after " + year + " years");
    }
    public CurrentInfo payNextDebt(){
        int paid = 0;

        while(numberOfDebts>0 && paid == 0){
            paid = evaluateIncome(year);
            chargeInterest();
            payOutInvestment();
            year++;
        }
        return getCurrentInfo();
    }

    private JsonElement toJSON(CurrentInfo info){
        return new Gson().toJsonTree(info);
    }

    public List<Deposit> getAllDeposits() {
        List<Deposit> depositList = new ArrayList<>();
        debtRepository.findAll().forEach(e -> depositList.add(e));
        investmentRepository.findAll().forEach(e -> depositList.add(e));
        return depositList;
    }
}



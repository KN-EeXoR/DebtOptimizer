package org.eexor.debtoptimizer.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Debt_handler {
    private List<Debt> list_of_debt;
    private double income;
    private double my_account;
    /**
     * @param income How much money we can spend evry month to pay off debts
     */
    Debt_handler(double income){
        list_of_debt = new ArrayList<>();
        this.income = income;
        my_account = 0;
    }
    /** Adding debt to your list of debts
     * @param your_debt object of class Debt
     */
    public void add_debt(Debt your_debt){
        list_of_debt.add(your_debt);
        list_of_debt.sort(null);
    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     * @param min_paymanet minimum monthly payment
     */
    public void add_debt(String name, double debt, double interest, double min_paymanet){
        list_of_debt.add(new Debt(name, debt, interest, min_paymanet));
        list_of_debt.sort(null);

    }
    /** Adding debt to your list of debts
     * @param name Distinguishable name which describes the debt
     * @param debt The amount of money you have to pay back
     * @param interest annual increase in the amount
     */
    public void add_debt(String name, double debt, double interest){
        list_of_debt.add(new Debt(name, debt, interest));
        list_of_debt.sort(null);
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
        double money = income;
        for (Debt debt : list_of_debt) {
            debt.pay_back(debt.min_paymanet());
            money -= debt.min_paymanet();
        }
        if (money < 0){
            System.out.println("Not enough money to may minimal payment");
            return;
        }
        for (int i = 0; i < list_of_debt.size(); i++) {
            money = list_of_debt.get(i).pay_back(money);
            if(list_of_debt.get(i).to_be_repaid() == 0)
                list_of_debt.remove(i);
                i--;
            if(money == 0)
                break;
        }
        my_account += money;
    }
    /**
     * Calculates the increase in our debts
     */
    public void charge_interest(){
        for (Debt debt : list_of_debt) {
            debt.charge_interest();
        }
    }
    /**
     * Prints a String which contains information about unpaid debts
     */
    public void get_all_debts_info(){
        for (Debt debt : list_of_debt) {
            System.out.println(debt.toString());
        }
    }
    /** returns an array that contains information about unpaid debts
     * @return returns a two-dimensional array where one row corresponds to one debt, each row has three columns with the respective: amount of debt, increase, minimum payment 
     */
    public double[][] get_table_with_all_debts_number_info(){
        int size = this.list_of_debt.size();
        double data[][] = new double[size][3];
        Debt tmp;
        for (int i =0 ; i<size; i++) {
            tmp = this.list_of_debt.get(i);
            data[i][0] = tmp.to_be_repaid();
            data[i][1] = tmp.interest();
            data[i][2] = tmp.min_paymanet();
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
                this.evaluate_income();
            }
            charge_interest();
        }
        double result = 0;
        for (Debt debt : list_of_debt) {
            result += debt.to_be_repaid();
        }
        return my_account - result;
    }

}


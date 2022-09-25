package org.eexor.debtoptimizer.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Debt_handler {
    private List<Debt> list_of_debt;
    private double income;
    private double my_account;
    Debt_handler(double income){
        list_of_debt = new ArrayList<>();
        this.income = income;
        my_account = 0;
    }
    public void add_debt(Debt your_debt){
        list_of_debt.add(your_debt);
        list_of_debt.sort(null);
    }
    public void add_debt(String name, double debt, double interest, double min_paymanet){
        list_of_debt.add(new Debt(name, debt, interest, min_paymanet));
        list_of_debt.sort(null);

    }public void add_debt(String name, double debt, double interest){
        list_of_debt.add(new Debt(name, debt, interest));
        list_of_debt.sort(null);
    }
    public int nuber_of_debts(){
        return list_of_debt.size();
    }
    public void change_income(double income){
        this.income = income;
    }
    public String get_debt_info(int indeks){
        return list_of_debt.get(indeks).toString();
    }
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
    public void charge_interest(){
        for (Debt debt : list_of_debt) {
            debt.charge_interest();
        }
    }
    public void get_all_debts_info(){
        for (Debt debt : list_of_debt) {
            System.out.println(debt.toString());
        }
    }
    public double[] get_table_with_all_debts_info(){
        int size = this.list_of_debt.size();
        double data[] = new double[size*3];
        Debt tmp;
        for (int i =0 ; i<size; i++) {
            tmp = this.list_of_debt.get(i);
            data[i*3] = tmp.to_be_repaid();
            data[i*3+1] = tmp.interest();
            data[i*3+2] = tmp.min_paymanet();
        }
        return data;
    }
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


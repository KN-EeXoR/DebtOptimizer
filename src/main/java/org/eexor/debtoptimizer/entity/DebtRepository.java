package org.eexor.debtoptimizer.entity;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DebtRepository {
    private final Map<Integer, Debt> liabilities = new TreeMap<>();

    public int getNextId() {
        return liabilities.keySet().size();
    }

    public List<Debt> getAll() {
        List<Debt> debtList = new ArrayList<>();
        for (Map.Entry<Integer, Debt> entry : liabilities.entrySet())
                debtList.add(entry.getValue());
        return debtList;
    }

    public Debt getDebt(int id) {
        return liabilities.getOrDefault(id, null);
    }

    public void addDebt(Debt debt) {
        debt.setId(getNextId());
        liabilities.put(debt.getId(), debt);
    }

    public void addDebt(int id, Debt debt) {
        debt.setId(id);
        liabilities.put(debt.getId(), debt);
    }

    public void addDebts(List<Debt> debts) {
        debts.forEach(this::addDebt);
    }

    public void removeDebt(int id) {
        liabilities.remove(id);
    }

    public void updateDebt(int id, Debt debt) {
        liabilities.put(id, debt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Map.Entry<Integer, Debt> entry : liabilities.entrySet()) {
                sb.append("\n\t{").append(entry.getKey()).append(", ").append(entry.getValue()).append("],");
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}

package org.eexor.debtoptimizer.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RepositoryController {

    @Autowired
    DebtRepository debtRepository;

    @GetMapping("/getDebts")
    public List<Debt> getDebts() {
        return debtRepository.getAll();
    }

    @GetMapping("/getDebt/{id}")
    public Debt getById(@PathVariable("id") int id) {
        return debtRepository.getDebt(id);
    }

    @PostMapping("/addDebt")
    public void addDebt(@RequestBody Debt debt) {
        debtRepository.addDebt(debt);
    }

    @PostMapping("/addDebts")
    public void addDebts(@RequestBody List<Debt> debtList) {
        debtRepository.addDebts(debtList);
    }

    @PostMapping("/removeDebt/{id}")
    public void removeDebt(
            @PathVariable("id") int id) {
        debtRepository.removeDebt(id);
    }

    @PutMapping("/updateDebt/{id}")
    public void modifyDebt(
            @PathVariable("id") int id,
            @RequestBody Debt newDebt) {
        Debt foundDebt = debtRepository.getDebt(id);
        if (foundDebt != null)
            debtRepository.updateDebt(id, newDebt);
    }
}

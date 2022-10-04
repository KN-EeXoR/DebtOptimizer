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
        return (List<Debt>)debtRepository.findAll();
    }

    @GetMapping("/getDebt/{id}")
    public Debt getById(@PathVariable("id") long id) {
        return debtRepository.findById(id).orElse(null);
    }

    @PostMapping("/addDebt")
    public void addDebt(@RequestBody Debt debt) {
        debtRepository.save(debt);
    }

    @PostMapping("/addDebts")
    public void addDebts(@RequestBody List<Debt> debtList) {
        debtRepository.saveAll(debtList);
    }

    @PostMapping("/removeDebt/{id}")
    public void removeDebt(
            @PathVariable("id") long id) {
        debtRepository.deleteById(id);
    }

    @PutMapping("/updateDebt/{id}")
    public void modifyDebt(
            @PathVariable("id") long id,
            @RequestBody double debt) {
        Debt foundDebt = debtRepository.findById(id).orElse(null);
        foundDebt.setDebt(debt);
        debtRepository.save(foundDebt);
    }
}

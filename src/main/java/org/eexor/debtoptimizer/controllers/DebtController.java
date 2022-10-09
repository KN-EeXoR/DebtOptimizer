package org.eexor.debtoptimizer.controllers;

import org.eexor.debtoptimizer.entity.Debt;
import org.eexor.debtoptimizer.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/debt")
public class DebtController implements ControllerBase<Debt> {

    @Autowired
    DebtRepository debtRepository;

    @GetMapping("/getAll")
    public List<Debt> getAll() {
        return (List<Debt>)debtRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Debt getById(@PathVariable("id") long id) {
        return debtRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public void add(@RequestBody Debt debt) {
        debtRepository.save(debt);
    }

    @PostMapping("/addMany")
    public void addMany(@RequestBody List<Debt> debtList) {
        debtRepository.saveAll(debtList);
    }

    @PostMapping("/remove/{id}")
    public void remove(
            @PathVariable("id") long id) {
        debtRepository.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void update(
            @PathVariable("id") long id,
            @RequestBody Debt newDebt) {
        Debt foundDebt = debtRepository.findById(id).orElse(null);
        if (foundDebt != null) {
            newDebt.setId(foundDebt.getId());
            debtRepository.save(newDebt);
        }
    }
}

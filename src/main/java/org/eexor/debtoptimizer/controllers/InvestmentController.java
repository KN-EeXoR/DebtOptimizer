package org.eexor.debtoptimizer.controllers;

import org.eexor.debtoptimizer.entity.Investment;
import org.eexor.debtoptimizer.repositories.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investment")
public class InvestmentController implements ProductControllerBase<Investment> {

    private final InvestmentRepository investmentRepository;

    @Autowired
    public InvestmentController(InvestmentRepository investmentRepository) {
        this.investmentRepository = investmentRepository;
    }

    @GetMapping("/getAll")
    public List<Investment> getAll() {
        return (List<Investment>)investmentRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Investment getById(@PathVariable("id") long id) {
        return investmentRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public void add(@RequestBody Investment investment) {
        investmentRepository.save(investment);
    }

    @PostMapping("/addMany")
    public void addMany(@RequestBody List<Investment> investmentList) {
        investmentRepository.saveAll(investmentList);
    }

    @PostMapping("/remove/{id}")
    public void remove(
            @PathVariable("id") long id) {
        investmentRepository.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void update(
            @PathVariable("id") long id,
            @RequestBody Investment newInvestment) {
        Investment foundInvestment = investmentRepository.findById(id).orElse(null);
        if (foundInvestment != null) {
            newInvestment.setId(foundInvestment.getId());
            investmentRepository.save(newInvestment);
        }
    }
}

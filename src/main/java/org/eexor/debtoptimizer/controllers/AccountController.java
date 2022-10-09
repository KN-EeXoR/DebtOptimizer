package org.eexor.debtoptimizer.controllers;

import org.eexor.debtoptimizer.entity.Deposit;
import org.eexor.debtoptimizer.service.DepositHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class AccountController {

    private final DepositHandler depositHandler;

    @Autowired
    public AccountController(DepositHandler depositHandler) {
        this.depositHandler = depositHandler;
    }

    @PostMapping("/setIncome/{amount}")
    public void setIncome(@PathVariable("amount") Double amount) {
        depositHandler.setIncome(amount);
    }

    @GetMapping("/getAllDeposits")
    public List<Deposit> getAll() {
        return depositHandler.getAllDeposits();
    }

}
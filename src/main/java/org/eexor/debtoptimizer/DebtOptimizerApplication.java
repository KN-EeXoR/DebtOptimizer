package org.eexor.debtoptimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DebtOptimizerApplication {

    public static void main(String[] args) {
        /*
        DebtRepository repo = new DebtRepository();
        List<Debt> debtList = List.of(
                new Debt("car", 10_000, 0.05, 300),
                new Debt("PhD", 500_000, 0.01, 1_000)
        );
        repo.addDebts(debtList);
        System.out.println(repo);
        */
        SpringApplication.run(DebtOptimizerApplication.class, args);
    }



}

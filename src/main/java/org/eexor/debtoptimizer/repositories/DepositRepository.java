package org.eexor.debtoptimizer.repositories;

import org.eexor.debtoptimizer.entity.Debt;
import org.eexor.debtoptimizer.entity.Deposit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long> {
    @Query("SELECT d FROM Deposit d WHERE d.isActive = true")
    List<Deposit> findAllActive();
}

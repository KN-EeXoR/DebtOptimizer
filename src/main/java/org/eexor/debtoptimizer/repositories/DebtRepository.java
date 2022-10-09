package org.eexor.debtoptimizer.repositories;

import org.eexor.debtoptimizer.entity.Debt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends CrudRepository<Debt, Long> {
    @Query("SELECT d FROM Debt d WHERE d.isActive = true")
    List<Debt> findAllActive();
}

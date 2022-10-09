package org.eexor.debtoptimizer.repositories;

import org.eexor.debtoptimizer.entity.Debt;
import org.eexor.debtoptimizer.entity.Investment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends CrudRepository<Investment, Long> {
    @Query("SELECT i FROM Investment i WHERE i.isActive = true")
    List<Investment> findAllActive();
}

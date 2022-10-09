package org.eexor.debtoptimizer.repositories;

import org.eexor.debtoptimizer.entity.Debt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtRepository extends CrudRepository<Debt, Long> {}

package org.eexor.debtoptimizer.repositories;

import org.eexor.debtoptimizer.entity.Investment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends CrudRepository<Investment, Long> {}

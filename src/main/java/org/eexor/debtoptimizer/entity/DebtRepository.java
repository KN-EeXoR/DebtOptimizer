package org.eexor.debtoptimizer.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends CrudRepository<Debt, Long> {}

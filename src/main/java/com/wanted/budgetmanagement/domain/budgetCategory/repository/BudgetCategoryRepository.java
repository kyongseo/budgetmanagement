package com.wanted.budgetmanagement.domain.budgetCategory.repository;

import com.wanted.budgetmanagement.domain.budgetCategory.entity.BudgetCategory;
import com.wanted.budgetmanagement.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory,Long> {

    Optional<BudgetCategory> findByName(String name);
}

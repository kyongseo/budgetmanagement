package com.wanted.budgetmanagement.domain.budget.repository;

import com.wanted.budgetmanagement.api.budget.dto.BudgetRecommendListResponse;
import com.wanted.budgetmanagement.api.budget.dto.BudgetRecommendResponse;
import com.wanted.budgetmanagement.api.expenditure.dto.ExpenditureRecommend;
import com.wanted.budgetmanagement.domain.budget.entity.Budget;
import com.wanted.budgetmanagement.domain.budgetCategory.entity.BudgetCategory;
import com.wanted.budgetmanagement.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
    Budget findByCategoryAndPeriodAndUser(BudgetCategory category, LocalDate period, User user);

    @Query("select new com.wanted.budgetmanagement.api.budget.dto.BudgetRecommendResponse(" +
            "category, round(:totalAmount  * ((round(sum(money) * 100 / (select sum(money) from Budget), 0)) / 100), 0) as average) " +
            "from Budget " +
            "group by category")
    List<BudgetRecommendResponse> findByAverage(@Param("totalAmount") long totalAmount);

    @Query("select new com.wanted.budgetmanagement.api.expenditure.dto.ExpenditureRecommend(" +
            "category, round(sum(money) / :period, -3) as todayExpenditurePossibleMoney) " +
            "from Budget " +
            "where user = :user AND period = :start " +
            "group by category")
    List<ExpenditureRecommend> findByExpenditureRecommend(@Param("user") User user, @Param("start") LocalDate start, @Param("period") long period);
}

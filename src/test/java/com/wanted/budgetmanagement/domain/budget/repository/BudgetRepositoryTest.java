package com.wanted.budgetmanagement.domain.budget.repository;

import com.wanted.budgetmanagement.api.budget.dto.BudgetRecommendResponse;
import com.wanted.budgetmanagement.domain.budget.entity.Budget;
import com.wanted.budgetmanagement.domain.budgetCategory.entity.BudgetCategory;
import com.wanted.budgetmanagement.domain.budgetCategory.repository.BudgetCategoryRepository;
import com.wanted.budgetmanagement.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class BudgetRepositoryTest {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetCategoryRepository categoryRepository;

    @DisplayName("예산 저장")
    @Test
    @WithMockUser
    void budgetSetting() {
        // given
        BudgetCategory category = new BudgetCategory(1L, "식비");
        categoryRepository.save(category);
        LocalDate date = LocalDate.parse("2023-11-11");
        User user = new User(1L, "email@gmail.com", "password", null);
        Budget budget = new Budget(1L, user, category, 100000, date);

        // when
        Budget saveBudget = budgetRepository.save(budget);

        // then
        assertAll(
                () -> assertThat(saveBudget.getCategory().getName()).isEqualTo(budget.getCategory().getName()),
                () -> assertThat(saveBudget.getId()).isEqualTo(budget.getId()),
                () -> assertThat(saveBudget.getMoney()).isEqualTo(budget.getMoney()),
                () -> assertThat(saveBudget.getUser().getId()).isEqualTo(budget.getUser().getId()),
                () -> assertThat(saveBudget.getPeriod()).isEqualTo(budget.getPeriod())
        );
    }

    @DisplayName("예산 추천 쿼리 동작 확인")
    @Test
    void budgetRecommend() {
        // given
        long totalAmount = 1000000L;

        // when
        List<BudgetRecommendResponse> responses = budgetRepository.findByAverage(totalAmount);

        // then
        assertThat(responses).isNotNull();
    }

}

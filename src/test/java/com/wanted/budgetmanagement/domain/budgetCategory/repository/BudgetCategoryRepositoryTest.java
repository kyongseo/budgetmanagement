package com.wanted.budgetmanagement.domain.budgetCategory.repository;

import com.wanted.budgetmanagement.domain.budgetCategory.entity.BudgetCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class BudgetCategoryRepositoryTest {

    @Autowired
    private BudgetCategoryRepository categoryRepository;

    @DisplayName("예산 카테고리 목록 전체 조회")
    @Test
    void categoryList() {
        // given
        BudgetCategory category = new BudgetCategory(1L, "식비");
        categoryRepository.save(category);

        // when
        List<BudgetCategory> list = categoryRepository.findAll();

        // then
        assertThat(list.get(0).getName()).isEqualTo("식비");
    }

}

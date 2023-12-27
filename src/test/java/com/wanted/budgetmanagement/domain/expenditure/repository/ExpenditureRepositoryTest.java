package com.wanted.budgetmanagement.domain.expenditure.repository;

import com.wanted.budgetmanagement.domain.budgetCategory.entity.BudgetCategory;
import com.wanted.budgetmanagement.domain.budgetCategory.repository.BudgetCategoryRepository;
import com.wanted.budgetmanagement.domain.expenditure.entity.Expenditure;
import com.wanted.budgetmanagement.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ExpenditureRepositoryTest {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    @Autowired
    private BudgetCategoryRepository categoryRepository;

    @DisplayName("지출 저장 성공")
    @Test
    void expenditureCreate() {
        // given
        BudgetCategory category = new BudgetCategory(1L, "식비");
        categoryRepository.save(category);
        User user = new User(1L, "email@gmail.com", "password", null);
        LocalDate date = LocalDate.parse("2023-11-11");
        Expenditure expenditure = new Expenditure(1L, "저녁값 지출", date, category, user, false, 20000L);

        // when
        Expenditure saveExpenditure = expenditureRepository.save(expenditure);

        // then
        assertAll(
                () -> assertThat(saveExpenditure.getCategory().getName()).isEqualTo(expenditure.getCategory().getName()),
                () -> assertThat(saveExpenditure.getMemo()).isEqualTo(expenditure.getMemo()),
                () -> assertThat(saveExpenditure.getPeriod()).isEqualTo(expenditure.getPeriod()),
                () -> assertThat(saveExpenditure.getUser().getId()).isEqualTo(expenditure.getUser().getId()),
                () -> assertThat(saveExpenditure.getMoney()).isEqualTo(expenditure.getMoney())

        );

    }

    @DisplayName("지출 상세 조회")
    @Test
    void expenditureDetail() {
        // given
        BudgetCategory category = new BudgetCategory(1L, "식비");
        categoryRepository.save(category);
        User user = new User(1L, "email@gmail.com", "password", null);
        LocalDate date = LocalDate.parse("2023-11-11");
        Expenditure expenditure = new Expenditure(1L, "저녁값 지출", date, category, user, false, 20000L);
        expenditureRepository.save(expenditure);

        // when
        Optional<Expenditure> expenditure1 = expenditureRepository.findById(expenditure.getId());

        // then
        assertAll(
                () -> assertThat(expenditure1.get().getCategory().getName()).isEqualTo(expenditure.getCategory().getName()),
                () -> assertThat(expenditure1.get().getMemo()).isEqualTo(expenditure.getMemo()),
                () -> assertThat(expenditure1.get().getPeriod()).isEqualTo(expenditure.getPeriod()),
                () -> assertThat(expenditure1.get().getUser().getId()).isEqualTo(expenditure.getUser().getId()),
                () -> assertThat(expenditure1.get().getMoney()).isEqualTo(expenditure.getMoney())

        );

    }

    @DisplayName("지출 삭제 성공")
    @Test
    void expenditureDelete() {
        // given
        BudgetCategory category = new BudgetCategory(1L, "식비");
        categoryRepository.save(category);
        User user = new User(1L, "email@gmail.com", "password", null);
        LocalDate date = LocalDate.parse("2023-11-11");
        Expenditure expenditure = new Expenditure(1L, "저녁값 지출", date, category, user, false, 20000L);
        expenditureRepository.save(expenditure);

        // when
        expenditureRepository.delete(expenditure);
        Optional<Expenditure> findExpenditure = expenditureRepository.findById(expenditure.getId());

        // then
        assertThat(findExpenditure).isEmpty();

    }
}

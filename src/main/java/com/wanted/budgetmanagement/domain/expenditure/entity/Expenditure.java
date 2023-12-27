package com.wanted.budgetmanagement.domain.expenditure.entity;

import com.wanted.budgetmanagement.api.expenditure.dto.ExpenditureUpdateRequest;
import com.wanted.budgetmanagement.domain.budgetCategory.entity.BudgetCategory;
import com.wanted.budgetmanagement.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String memo;

    private LocalDate period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BudgetCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column
    private boolean excludingTotal;

    @Column
    private long money;

    public void updateExpenditure(ExpenditureUpdateRequest request, BudgetCategory category) {
        this.money = request.getMoney();
        this.category = category;
        this.period = request.getPeriod();
        this.memo = request.getMemo();
    }

    public void excludingTotalUpdate(boolean excludingTotal) {
        this.excludingTotal = excludingTotal;
    }
}

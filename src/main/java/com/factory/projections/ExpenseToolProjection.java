package com.factory.projections;

import java.math.BigDecimal;

public interface ExpenseToolProjection {

    Integer getId();

    BigDecimal getAmount();

    String getDescription();

    String getCreatedAt();

    BigDecimal getIncomePrice();

    BigDecimal getExpensePrice();

    String getToolName();

    String getToolTypeName();

    String getExpenseTypeName();

    Integer getExpenseTypeId();

    Integer getToolTypeId();

    BigDecimal getSumExpense();

    Integer getIncomeId();

    Integer getToolId();

//    BigDecimal getLeftAmount();
}

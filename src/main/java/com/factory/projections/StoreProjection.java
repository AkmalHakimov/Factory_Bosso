package com.factory.projections;

import java.math.BigDecimal;

public interface StoreProjection {

    Integer getId();

    String getToolName();

    String getToolTypeName();

    BigDecimal getTotalIncomeAmount();

    BigDecimal getTotalExpenseAmount();

    BigDecimal getLeftAmount();
}

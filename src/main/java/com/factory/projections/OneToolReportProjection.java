package com.factory.projections;

import java.math.BigDecimal;

public interface OneToolReportProjection {
    Integer getId();
    String getName();
    BigDecimal getAmount();
    BigDecimal getExpensePrice();
    BigDecimal getTotalExpense();
    BigDecimal getTotalIncome();
    BigDecimal getRatio();
}

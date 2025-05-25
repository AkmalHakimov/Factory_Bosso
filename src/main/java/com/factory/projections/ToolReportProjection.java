package com.factory.projections;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.Target;
import java.math.BigDecimal;

public interface ToolReportProjection {

    Integer getId();

    String getMarka();

    String getName();

    BigDecimal getTotalIncomeAmount();

    BigDecimal getTotalExpenseAmount();

    BigDecimal getTotalExpensePrice();
    BigDecimal getTotalIncomePrice();
    BigDecimal getTotalExpensePriceSum();
}

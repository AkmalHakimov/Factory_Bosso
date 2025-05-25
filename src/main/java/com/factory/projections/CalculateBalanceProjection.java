package com.factory.projections;

import java.math.BigDecimal;

public interface CalculateBalanceProjection {

    BigDecimal getTotalIncomeAmount();

    BigDecimal getTotalExpensePrice();
}

package com.factory.projections;

import java.math.BigDecimal;

public interface ExpenseTypeProjection {

    Integer getId();

    BigDecimal getAmount();

    String getCreatedAt();

    String getName();
}

package com.factory.projections;

import java.math.BigDecimal;

public interface IncomeToolProjection {

    Integer getId();

    BigDecimal getAmount();

    String getCreatedAt();

    String getPaymentType();

    BigDecimal getPrice();

    String getToolName();

    Integer getToolId();

    String getToolTypeName();
    String getToolTypeId();

    BigDecimal getSumIncome();

    //    @Value("#{target.category.name}")
//    String getCategoryName();
}

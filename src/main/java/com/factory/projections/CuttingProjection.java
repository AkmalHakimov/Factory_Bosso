package com.factory.projections;

import java.math.BigDecimal;

public interface CuttingProjection {

    Integer getId();
    Integer getWorkerId();
    Integer getArticleId();

    String getFirstName();

    String getLastName();

    String getCreatedAt();

    String getName();

    BigDecimal getOrderNum();

    String getSideOption();

    BigDecimal getWidth();

    BigDecimal getHeight();

    BigDecimal getPerimeter();
    BigDecimal getTotalPerimeter();

    BigDecimal getReadyProdCount();

    BigDecimal getRascenka();

    BigDecimal getCuttingPrice();
}

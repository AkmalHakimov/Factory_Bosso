package com.factory.projections;


import java.math.BigDecimal;
import java.time.LocalDate;

public interface BoxProjection {

    Integer getId();
    Integer getWorkerId();
    Integer getArticleId();

    String getFirstName();

    String getLastName();

    String getCreatedAt();

    String getName();


    String getOrderNum();

    BigDecimal getBoxCount();

    BigDecimal getBoxContentCount();

    BigDecimal getPackedCount();

    BigDecimal getSquare();

    BigDecimal getTotalSquare();

    BigDecimal getRascenka();

    BigDecimal getSewingAmount();
}

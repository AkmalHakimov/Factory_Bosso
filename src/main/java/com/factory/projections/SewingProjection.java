package com.factory.projections;

import java.math.BigDecimal;

public interface SewingProjection {

    Integer getId();

    Integer getWorkerId();

    Integer getArticleId();
    String getFirstName();

    String getLastName();

    String getCreatedAt();

    String getName();

    BigDecimal getOrderNum();

    String getArtMat();

    BigDecimal getCount();

    BigDecimal getArtPerimeter();

    BigDecimal getTotalPerimeter();

    BigDecimal get();

    BigDecimal getSewingPrice();

    BigDecimal getChipCount();

    BigDecimal getChipPrice();

    BigDecimal getCleaningCount();

    BigDecimal getCleaningPrice();

    BigDecimal getButtonOpenCount();

    BigDecimal getButtonOpenPrice();

    BigDecimal getYarnOpenCount();

    BigDecimal getYarnOpenPrice();

    BigDecimal getBlueLabel();

    BigDecimal getBetPrice();

    BigDecimal getBlueLabelPrice();

    BigDecimal getYellowChip();

    BigDecimal getYellowChipPrice();

    BigDecimal getPlankDrawing();

    BigDecimal getPlankPrice();

    BigDecimal getPackBag();

    BigDecimal getMakePackPrice();

}

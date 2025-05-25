package com.factory.projections;

import java.math.BigDecimal;

public interface ArticleProjection {
    Integer getId();

    BigDecimal getBetPrice();

    BigDecimal getBlueLabelPrice();

    BigDecimal getButtonOpenPrice();

    BigDecimal getChipPrice();

    BigDecimal getCleaningPrice();

    BigDecimal getCuttingPrice();

    BigDecimal getHeight();

    BigDecimal getLabelPrice();

    BigDecimal getMakePackPrice();

    String getName();

    BigDecimal getPackPrice();

    BigDecimal getPlankPrice();

    BigDecimal getSideNum();

    BigDecimal getWidth();

    BigDecimal getYarnOpenPrice();

    BigDecimal getYarnPrice();

    BigDecimal getYellowChipPrice();

    BigDecimal getSewingPerimeter();

    BigDecimal getCuttingPerimeter();

    BigDecimal getSquare();
}

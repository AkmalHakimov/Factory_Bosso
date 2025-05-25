package com.factory.projections;

import java.math.BigDecimal;

public interface PieReportProjection {
    BigDecimal getSalfetkaTotal();

    BigDecimal getSumkaTotal();

    BigDecimal getChoyshabTotal();

    BigDecimal getChoyshabTotalAm();

    BigDecimal getSumkaTotalAm();

    BigDecimal getSalfetkaTotalAm();
}

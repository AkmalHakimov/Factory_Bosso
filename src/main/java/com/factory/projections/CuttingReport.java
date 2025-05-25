package com.factory.projections;

import java.math.BigDecimal;

public interface CuttingReport {

    Integer getId();

    String getFirstName();

    String getLastName();

    BigDecimal getTotalCount();

    BigDecimal getTotalMeter();

    BigDecimal getTotalPrice();
}

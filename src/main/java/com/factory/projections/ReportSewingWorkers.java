package com.factory.projections;

import java.math.BigDecimal;

public interface ReportSewingWorkers {

    Integer getWorkerId(); // Worker ID

    String getFirstName(); // First name of the worker

    String getLastName(); // Last name of the worker

    BigDecimal getCount(); // Total count of items sewn by the worker

    BigDecimal getChipCount(); // Total chip count

    BigDecimal getChipPrice(); // Total price for chips

    BigDecimal getCleaningCount(); // Total cleaning count

    BigDecimal getCleaningPrice(); // Total price for cleaning

    BigDecimal getButtonOpenCount(); // Total button open count

    BigDecimal getButtonOpenPrice(); // Total price for button opening

    BigDecimal getYarnOpenCount(); // Total yarn open count

    BigDecimal getYarnOpenPrice(); // Total price for yarn opening

    BigDecimal getBlueLabel(); // Total blue labels used

    BigDecimal getBlueLabelPrice(); // Total price for blue labels

    BigDecimal getYellowChip(); // Total yellow chips used

    BigDecimal getYellowChipPrice(); // Total price for yellow chips

    BigDecimal getTotalPerimeter(); // Total perimeter calculated for sewing

    BigDecimal getReadyProdCount(); // Ready product count from cutting

    BigDecimal getTotalPerimeterCutting(); // Total perimeter for cutting

    BigDecimal getCuttingPrice(); // Total cutting price

    BigDecimal getBoxCount(); // Total number of boxes

    BigDecimal getBoxPrice(); // Total price for boxes

    BigDecimal getBoxCountTotal(); // Total box count with content factored in

    BigDecimal getPlankCount(); // Total price for planks
    BigDecimal getPlankPrice(); // Total price for planks

    BigDecimal getMakePackPrice(); // Total price for making packs
    BigDecimal getMakePackCount(); // Total price for making packs
    BigDecimal getSewingPrice(); // Total price for making packs
}

package com.factory.payload.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqPieReport {

    private LocalDate createdAt;
    private Integer monthDateNum;
    private Integer dateNumSal;
    private BigDecimal salAm;
    private Integer dateNumChoy;
    private BigDecimal choyAm;
    private Integer dateNumSumka;
    private BigDecimal sumkaAm;
}

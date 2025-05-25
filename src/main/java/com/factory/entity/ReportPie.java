package com.factory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class ReportPie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate createdAt;
    private Integer monthDateNum;
    private Integer dateNumSal;
    private BigDecimal salAm;
    private Integer dateNumChoy;
    private BigDecimal choyAm;
    private Integer dateNumSumka;
    private BigDecimal sumkaAm;
}

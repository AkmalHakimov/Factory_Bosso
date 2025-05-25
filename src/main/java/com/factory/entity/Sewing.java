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
public class Sewing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate createdAt;
    private BigDecimal orderNum;
    private BigDecimal count;
    private BigDecimal chipCount;
    private BigDecimal cleaningCount;
    private BigDecimal buttonOpenCount;
    private BigDecimal yarnOpenCount;
    private BigDecimal blueLabel;
    private BigDecimal yellowChip;
    private BigDecimal plankDrawing;
    private BigDecimal packBag;
    private String artMat;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Worker worker;
}

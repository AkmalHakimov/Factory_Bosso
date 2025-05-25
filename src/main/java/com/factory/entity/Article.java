package com.factory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Article {
    @Id
    @SequenceGenerator(name = "article_seq", sequenceName = "article_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    private Integer id;
    private String name;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal sideNum;
    private BigDecimal betPrice;
    private BigDecimal cuttingPrice;
    private BigDecimal packPrice;
    private BigDecimal yarnPrice;
    private BigDecimal chipPrice;
    private BigDecimal cleaningPrice;
    private BigDecimal buttonOpenPrice;
    private BigDecimal yarnOpenPrice;
    private BigDecimal blueLabelPrice;
    private BigDecimal labelPrice;
    private BigDecimal yellowChipPrice;
    private BigDecimal plankPrice;
    private BigDecimal makePackPrice;

}

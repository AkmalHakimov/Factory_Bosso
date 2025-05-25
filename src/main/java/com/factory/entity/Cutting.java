package com.factory.entity;


import com.factory.enums.SideOption;
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
public class Cutting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate createdAt;
    private BigDecimal readyProdCount;
    private BigDecimal orderNum;

    @Enumerated(EnumType.STRING)
    private SideOption sideOption;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Worker worker;
}

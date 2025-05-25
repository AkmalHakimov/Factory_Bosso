package com.factory.entity;

import com.factory.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class IncomeTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate created_at;

    private BigDecimal amount;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    private Tool tool;

    @ManyToOne
    private ToolType toolType;
}

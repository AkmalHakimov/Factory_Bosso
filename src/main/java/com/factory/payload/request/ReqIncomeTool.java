package com.factory.payload.request;

import com.factory.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqIncomeTool {

    private LocalDate createdAt;

    private BigDecimal amount;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Integer toolId;

    private Integer toolTypeId;
}

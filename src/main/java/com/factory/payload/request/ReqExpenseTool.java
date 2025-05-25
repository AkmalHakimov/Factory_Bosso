package com.factory.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class ReqExpenseTool {


    private LocalDate createdAt;

    private BigDecimal amount;

    private String description;

    private BigDecimal price;

    private Integer toolId;
    private Integer toolTypeId;
    private Integer expenseTypeId;
}

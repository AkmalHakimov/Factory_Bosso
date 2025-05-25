package com.factory.payload.request;

import com.factory.entity.Article;
import com.factory.entity.Worker;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.ManyToOne;
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
public class ReqSewing {

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


    private Integer articleId;

    private Integer workerId;
}

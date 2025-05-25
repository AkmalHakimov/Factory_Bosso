package com.factory.payload.request;


import com.factory.entity.Article;
import com.factory.entity.Worker;
import com.factory.enums.SideOption;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ReqCutting {
    private LocalDate createdAt;
    private BigDecimal readyProdCount;
    private BigDecimal orderNum;

    private String sideOption;

    private Integer articleId;

    private Integer workerId;
}

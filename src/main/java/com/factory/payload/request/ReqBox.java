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
public class ReqBox {

    private LocalDate createdAt;
    private BigDecimal boxContentCount;
    private String orderNum;
    private BigDecimal boxCount;

    private Integer articleId;

    private Integer workerId;
}

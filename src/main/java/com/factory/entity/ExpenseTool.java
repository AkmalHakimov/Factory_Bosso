package com.factory.entity;

import com.factory.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ExpenseTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "dd-MM-yyyy") // Adjust to match the frontend date format
    private LocalDate createdAt;

    private BigDecimal amount;

    @Column(length = 1000) // Expanding description length to 1000 characters
    private String description;

    @Column(nullable = true) // Explicitly specifying price can be null
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "expense_type_id", nullable = true) // `expenseType` can be null
    private ExpenseType expenseType;

    @ManyToOne
    private Tool tool;

    @ManyToOne
    private ToolType toolType;
}

package com.factory.services.ExpenseToolService;

import com.factory.entity.ExpenseTool;
import com.factory.payload.request.ReqExpenseTool;
import com.factory.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExpenseToolServiceImpl implements ExpenseToolService {

    private final ExpenseToolRepo expenseToolRepo;
    private final ToolRepo toolRepo;
    private final ToolTypeRepo toolTypeRepo;
    private final IncomeToolRepo incomeToolRepo;
    private final ExpenseTypeRepo expenseTypeRepo;

    @Override
    public HttpEntity<?> get(String search, Integer toolId, Integer page, Integer offset,LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(expenseToolRepo.getExpenseTools(search, toolId,
                date,
                pageable));
    }

    @Override
    public HttpEntity<?> post(ReqExpenseTool expenseTool) {

        BigDecimal totalIncomeAmount = incomeToolRepo.totalOfSpecifiedIncome(expenseTool.getToolId(),expenseTool.getToolTypeId());
        BigDecimal totalExpenseAmount = expenseToolRepo.totalOfSpecifiedExpense(expenseTool.getToolId(),expenseTool.getToolTypeId());



//        if (totalExpenseAmount.equals(totalIncomeAmount)) {
//            return ResponseEntity.status(404).body("Berilgan materialda chiqim miqdori kirim miqdoriga teng!");
//        }
//
//        if (totalExpenseAmount.add(expenseTool.getAmount()).compareTo(totalIncomeAmount) > 0) {
//            BigDecimal difference = totalIncomeAmount.subtract(totalExpenseAmount);
//            return ResponseEntity.status(404).body("Chiqim miqdori " + difference + " dan ko'p bo'lmasligi kerak");
//        }

//        if(expenseTool.getExpenseTypeId() == 0){
            expenseToolRepo.save(ExpenseTool.builder()
                    .tool(toolRepo.findById(expenseTool.getToolId()).orElseThrow())
                    .toolType(toolTypeRepo.findById(expenseTool.getToolTypeId()).orElseThrow())
                    .expenseType(
                            expenseTool.getExpenseTypeId() == null
                                    ? null
                                    : expenseTypeRepo.findById(expenseTool.getExpenseTypeId()).orElse(null)
                    )

                    .price(expenseTool.getPrice())
                    .amount(expenseTool.getAmount())
                    .createdAt(expenseTool.getCreatedAt())
                    .description(expenseTool.getDescription())
                    .build());
//        }else {
//            expenseToolRepo.save(ExpenseTool.builder()
//                    .tool(toolRepo.findById(expenseTool.getToolId()).orElseThrow())
//                    .toolType(toolTypeRepo.findById(expenseTool.getToolTypeId()).orElseThrow())
//                    .expenseType(expenseTypeRepo.findById(expenseTool.getExpenseTypeId()).orElseThrow())
//                    .price(expenseTool.getPrice())
//                    .amount(expenseTool.getAmount())
//                    .createdAt(expenseTool.getCreatedAt())
//                    .description(expenseTool.getDescription())
//                    .build());
//        }

        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> delete(Integer expenseToolId) {
        expenseToolRepo.deleteById(expenseToolId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(Integer expenseToolId, ReqExpenseTool expenseTool) {
        BigDecimal totalAmount = incomeToolRepo.totalOfSpecifiedIncome(expenseTool.getToolId(),expenseTool.getToolTypeId());

//        if (expenseTool.getAmount().compareTo(totalAmount) > 0) {
//            BigDecimal difference = expenseTool.getAmount().subtract(totalAmount);
//            return ResponseEntity.status(404).body("Chiqim miqdori " + difference + " dan ko'p bo'lmasligi kerak");
//        }
        return ResponseEntity.ok(expenseToolRepo.save(ExpenseTool.builder()
                .id(expenseToolId)
                .toolType(toolTypeRepo.findById(expenseTool.getToolTypeId()).orElseThrow())
                .expenseType(expenseTypeRepo.findById(expenseTool.getExpenseTypeId()).orElseThrow())
                .tool(toolRepo.findById(expenseTool.getToolId()).orElseThrow())
                .price(expenseTool.getPrice())
                .amount(expenseTool.getAmount())
                .createdAt(expenseTool.getCreatedAt())
                .description(expenseTool.getDescription())
                .build()));
    }
}

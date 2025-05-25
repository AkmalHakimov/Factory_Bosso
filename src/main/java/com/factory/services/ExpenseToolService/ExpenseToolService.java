package com.factory.services.ExpenseToolService;

import com.factory.payload.request.ReqExpenseTool;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;

public interface ExpenseToolService {
    HttpEntity<?> get(String search, Integer toolId, Integer page, Integer offset,LocalDate date);

    HttpEntity<?> post(ReqExpenseTool expenseTool);

    HttpEntity<?> delete(Integer expenseToolId);

    HttpEntity<?> edit(Integer expenseToolId, ReqExpenseTool expenseTool);
}

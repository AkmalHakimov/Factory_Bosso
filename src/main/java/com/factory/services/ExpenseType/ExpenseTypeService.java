package com.factory.services.ExpenseType;

import com.factory.payload.request.ReqExpenseType;
import org.springframework.http.HttpEntity;

public interface ExpenseTypeService {
    HttpEntity<?> get(String search, Integer page, Integer offset);

    HttpEntity<?> post(ReqExpenseType reqExpenseType);

    HttpEntity<?> delete(Integer expenseTypeId);

    HttpEntity<?> edit(Integer expenseTypeId, ReqExpenseType reqExpenseType);

    HttpEntity<?> getCurrentDateTypes();
}

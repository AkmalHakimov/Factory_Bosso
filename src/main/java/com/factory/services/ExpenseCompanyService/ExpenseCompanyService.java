package com.factory.services.ExpenseCompanyService;


import com.factory.entity.ExpenseCompany;
import org.springframework.http.HttpEntity;

public interface ExpenseCompanyService {

    HttpEntity<?> get(String search, Integer page, Integer offset);

    HttpEntity<?> post(ExpenseCompany expenseCompany);

    HttpEntity<?> delete(Integer expenseCompanyId);

    HttpEntity<?> edit(Integer expenseCompanyId, ExpenseCompany expenseCompany);
}

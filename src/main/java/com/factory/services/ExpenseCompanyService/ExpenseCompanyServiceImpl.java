package com.factory.services.ExpenseCompanyService;

import com.factory.entity.ExpenseCompany;
import com.factory.repository.ExpenseCompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseCompanyServiceImpl implements ExpenseCompanyService {

    private final ExpenseCompanyRepo expenseCompanyRepo;

    @Override
    public HttpEntity<?> get(String search, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(expenseCompanyRepo.getExpenseCompany(pageable,search));
    }

    @Override
    public HttpEntity<?> post(ExpenseCompany expenseCompany) {
        return ResponseEntity.ok(expenseCompanyRepo.save(ExpenseCompany.builder()
                .name(expenseCompany.getName())
                .type(expenseCompany.getType())
                .price(expenseCompany.getPrice())
                .amount(expenseCompany.getAmount())
                .createdAt(expenseCompany.getCreatedAt())
                .description(expenseCompany.getDescription())
                .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer expenseCompanyId) {
        expenseCompanyRepo.deleteById(expenseCompanyId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(Integer expenseCompanyId, ExpenseCompany expenseCompany) {
        return ResponseEntity.ok(expenseCompanyRepo.save(ExpenseCompany.builder()
                .id(expenseCompanyId)
                .name(expenseCompany.getName())
                .type(expenseCompany.getType())
                .price(expenseCompany.getPrice())
                .amount(expenseCompany.getAmount())
                .createdAt(expenseCompany.getCreatedAt())
                .description(expenseCompany.getDescription())
                .build()));
    }
}

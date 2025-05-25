package com.factory.services.ExpenseType;

import com.factory.entity.ExpenseType;
import com.factory.payload.request.ReqExpenseType;
import com.factory.repository.ExpenseTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseTypeRepo expenseTypeRepo;

    @Override
    public HttpEntity<?> getCurrentDateTypes() {
        return ResponseEntity.ok(expenseTypeRepo.getExpenseTypesForExpenseTable());
    }

    @Override
    public HttpEntity<?> get(String search, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(expenseTypeRepo.getExpenseTypes(search, pageable));
    }

    @Override
    public HttpEntity<?> post(ReqExpenseType reqExpenseType) {
        return ResponseEntity.ok(expenseTypeRepo.save(ExpenseType.builder()
                .amount(reqExpenseType.getAmount())
                .created_at(reqExpenseType.getCreatedAt())
                .name(reqExpenseType.getName())
                .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer expenseTypeId) {
        expenseTypeRepo.deleteById(expenseTypeId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(Integer expenseTypeId, ReqExpenseType reqExpenseType) {
        return ResponseEntity.ok(expenseTypeRepo.save(ExpenseType.builder()
                .id(expenseTypeId)
                .amount(reqExpenseType.getAmount())
                .created_at(reqExpenseType.getCreatedAt())
                .name(reqExpenseType.getName())
                .build()));
    }
}

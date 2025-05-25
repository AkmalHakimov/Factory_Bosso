package com.factory.controller;

import com.factory.entity.ExpenseCompany;
import com.factory.entity.IncomeTool;
import com.factory.services.ExpenseCompanyService.ExpenseCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenseCompany")
@RequiredArgsConstructor
public class ExpenseCompanyController {

    private final ExpenseCompanyService expenseCompanyService;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset
    ){
        return expenseCompanyService.get(search,page,offset);
    }

    @PostMapping
    public HttpEntity<?> post(@RequestBody ExpenseCompany expenseCompany){
        return expenseCompanyService.post(expenseCompany);
    }

    @DeleteMapping("/{expenseCompanyId}")
    public HttpEntity<?> delete(@PathVariable Integer expenseCompanyId){
        return expenseCompanyService.delete(expenseCompanyId);
    }

    @PutMapping("/{expenseCompanyId}")
    public HttpEntity<?> edit(@PathVariable Integer expenseCompanyId, @RequestBody ExpenseCompany expenseCompany){
        return expenseCompanyService.edit(expenseCompanyId,expenseCompany);
    }
}

package com.factory.controller;

import com.factory.entity.ExpenseTool;
import com.factory.entity.IncomeTool;
import com.factory.payload.request.ReqExpenseTool;
import com.factory.services.ExpenseToolService.ExpenseToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/expenseTool")
@RequiredArgsConstructor
public class ExpenseToolController {

    private final ExpenseToolService expenseToolService;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") Integer toolId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ){
        return expenseToolService.get(search,toolId,page,offset,date);
    }

    @PostMapping
    public HttpEntity<?> post(@RequestBody ReqExpenseTool reqExpenseTool){
        return expenseToolService.post(reqExpenseTool);
    }

    @DeleteMapping("/{expenseToolId}")
    public HttpEntity<?> delete(@PathVariable Integer expenseToolId){
        return expenseToolService.delete(expenseToolId);
    }

    @PutMapping("/{expenseToolId}")
    public HttpEntity<?> edit(@PathVariable Integer expenseToolId, @RequestBody ReqExpenseTool reqExpenseTool){
        return expenseToolService.edit(expenseToolId,reqExpenseTool);
    }
}

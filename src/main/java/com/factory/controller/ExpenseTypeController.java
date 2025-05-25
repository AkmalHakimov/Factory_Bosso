package com.factory.controller;

import com.factory.payload.request.ReqExpenseType;
import com.factory.services.ExpenseType.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenseType")
@RequiredArgsConstructor
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset
    ){
        return expenseTypeService.get(search,page,offset);
    }

    @GetMapping("/currentDateTypes")
    public HttpEntity<?> getCurrentDateTypes(){
        return expenseTypeService.getCurrentDateTypes();
    }

    @PostMapping
    public HttpEntity<?> post(@RequestBody ReqExpenseType reqExpenseType){
        return expenseTypeService.post(reqExpenseType);
    }

    @DeleteMapping("/{expenseTypeId}")
    public HttpEntity<?> delete(@PathVariable Integer expenseTypeId){
        return expenseTypeService.delete(expenseTypeId);
    }

    @PutMapping("/{expenseTypeId}")
    public HttpEntity<?> edit(@PathVariable Integer expenseTypeId, @RequestBody ReqExpenseType reqExpenseType){
        return expenseTypeService.edit(expenseTypeId,reqExpenseType);
    }
}

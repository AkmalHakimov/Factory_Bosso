package com.factory.controller;

import com.factory.entity.IncomeTool;
import com.factory.entity.Tool;
import com.factory.payload.request.ReqIncomeTool;
import com.factory.services.IncomeToolService.IncomeToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/incomeTool")
@RequiredArgsConstructor
public class IncomeToolController {

    private final IncomeToolService incomeToolService;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") Integer toolId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam  LocalDate date
    ){
        return incomeToolService.get(search,toolId,page,offset,date);
    }

    @PostMapping
    public HttpEntity<?> post(@RequestBody ReqIncomeTool reqIncomeTool){
        return incomeToolService.post(reqIncomeTool);
    }

    @DeleteMapping("/{incomeToolId}")
    public HttpEntity<?> delete(@PathVariable Integer incomeToolId){
        return incomeToolService.delete(incomeToolId);
    }

    @PutMapping("/{incomeToolId}")
    public HttpEntity<?> edit(@PathVariable Integer incomeToolId, @RequestBody ReqIncomeTool reqIncomeTool){
        return incomeToolService.edit(incomeToolId,reqIncomeTool);
    }
}

package com.factory.controller;

import com.factory.payload.request.ReqTool;
import com.factory.projections.OneToolReportProjection;
import com.factory.repository.ExpenseToolRepo;
import com.factory.repository.IncomeToolRepo;
import com.factory.repository.ToolRepo;
import com.factory.services.ExcelService.ExcelService;
import com.factory.services.ToolService.ToolService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tool")
@RequiredArgsConstructor
public class ToolController {

    @Qualifier("toolServiceImpl")
    private final ToolService toolService;

    private final IncomeToolRepo incomeToolRepo;
    private final ExpenseToolRepo expenseToolRepo;
    private final ToolRepo toolRepo;

    private final ExcelService excelService;

    @GetMapping
    public HttpEntity<?> getTools(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") Integer toolTypeId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset
    ){
        return toolService.getTools(search,toolTypeId,page,offset);
    }

    @GetMapping("/one/{toolId}")
    public HttpEntity<?> getOneTool(@PathVariable Integer toolId){
        return toolService.getOneTool(toolId);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAllTools(){
        return toolService.getAllTools();
    }


    @GetMapping("/pieReport")
    public HttpEntity<?> getToolPieReport(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ){
        return toolService.getToolPieReport(date);
    }

    @GetMapping("/leftAmount")
    public HttpEntity<?> getLeftAmount(@RequestParam Integer toolId,@RequestParam Integer toolTypeId){
        BigDecimal totalIncomeAmount = incomeToolRepo.totalOfSpecifiedIncome(toolId,toolTypeId);
        BigDecimal totalExpenseAmount = expenseToolRepo.totalOfSpecifiedExpense(toolId,toolTypeId);

        BigDecimal leftAmount = totalIncomeAmount.subtract(totalExpenseAmount);
        return ResponseEntity.ok((leftAmount));
    }

    @GetMapping("/calculateBalance")
    public HttpEntity<?> getCalculateBalance(){
        return ResponseEntity.ok(toolRepo.calculateTotalExpensePriceAndTotalIncome());
    }

    @GetMapping("/oneToolReport/{toolId}")
    public HttpEntity<?> getOneToolRepo(@PathVariable Integer toolId, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date){
        List<OneToolReportProjection> oneToolReport = toolRepo.getOneToolReport(toolId,date);
        return ResponseEntity.ok(oneToolReport);
    }

    @PostMapping
    public HttpEntity<?> createTool(@RequestBody ReqTool reqTool){
        return toolService.createTool(reqTool);
    }

    @DeleteMapping("/{toolId}")
    public HttpEntity<?> deleteTool(@PathVariable Integer toolId){
        return toolService.deleteTool(toolId);
    }

    @PutMapping("/{toolId}")
    public HttpEntity<?> editTool(@PathVariable Integer toolId, @RequestBody ReqTool reqTool){
        return toolService.editTool(reqTool,toolId);
    }

    @GetMapping("/report")
    public HttpEntity<?> getReports(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ){
        return toolService.getReports(search,page,offset,date);
    }

    @GetMapping("/report/excel/download")
    public HttpEntity<?> downloadExcel(HttpServletResponse response) throws IOException, ParseException {
        return excelService.downloadReportExcel(response);
    }
}

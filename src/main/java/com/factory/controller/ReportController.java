package com.factory.controller;

import com.factory.payload.request.ReqIncomeTool;
import com.factory.payload.request.ReqPieReport;
import com.factory.services.ReportService.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/secondary")
    public HttpEntity<?> getReports(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ){
        return reportService.getSecondaryReports(search,page,offset,date);
    }

    @GetMapping("/store")
    public HttpEntity<?> getLeftAmountReport(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ){
        return reportService.getLeftAmountReport(search,page,offset,date);
    }

    @PostMapping
    public HttpEntity<?> post(@RequestBody ReqPieReport reqPieReport) {
        return reportService.post(reqPieReport);
    }
}

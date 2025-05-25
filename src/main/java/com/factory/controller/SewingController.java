package com.factory.controller;

import com.factory.payload.request.ReqBox;
import com.factory.payload.request.ReqSewing;
import com.factory.repository.SewingRepo;
import com.factory.services.ExcelService.ExcelService;
import com.factory.services.SewingService.SewingService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/sewing")
@RequiredArgsConstructor
public class SewingController {

    private final SewingService sewingService;
    private final ExcelService excelService;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam LocalDate date
    ){return sewingService.get(search,page,offset, date)
        ;
    }

    @GetMapping("/report/excel/download")
    public HttpEntity<?> downloadExcel(HttpServletResponse response, @RequestParam LocalDate date) throws IOException {
        return excelService.downloadWorkerExcel(response,date);
    }

    @GetMapping("/report/excel/list/download")
    public HttpEntity<?> downloadExcelForList(HttpServletResponse response, @RequestParam LocalDate date,@RequestParam Integer workerId) throws IOException {
        return excelService.downloadWorkerExcelForList(response,date,workerId);
    }

    @GetMapping("/report")
    public HttpEntity<?> getReport(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam Integer workerId,
            @RequestParam LocalDate date
    ){
        return sewingService.getWorkerReports(search,page,offset, date,workerId);
    }
    @GetMapping("/report/oneWorker/{workerId}")
    public HttpEntity<?> getReportForOneWorker(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @PathVariable Integer workerId,
            @RequestParam LocalDate date
    ){
        return sewingService.getReportForOneWorker(search,page,offset,workerId, date);
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody ReqSewing reqSewing){
        return sewingService.create(reqSewing);
    }

    @DeleteMapping("/{sewingId}")
    public HttpEntity<?> delete(@PathVariable Integer sewingId){
        return sewingService.delete(sewingId);
    }

    @PutMapping("/{sewingId}")
    public HttpEntity<?> edit(@PathVariable Integer sewingId, @RequestBody ReqSewing reqSewing){
        return sewingService.edit(reqSewing,sewingId);
    }
}

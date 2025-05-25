package com.factory.controller;

import com.factory.payload.request.ReqCutting;
import com.factory.services.CuttingService.CuttingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cutting")
@RequiredArgsConstructor
public class CuttingController {

    private final CuttingService cuttingService;

    @GetMapping
    public HttpEntity<?> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam  LocalDate date
    ){
        return cuttingService.get(search,page,offset, date);
    }

    @GetMapping("/report")
    public HttpEntity<?> getCuttingReports(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @RequestParam  LocalDate date
    ){
        return cuttingService.getCuttingReports(search,page,offset, date);
    }

    @GetMapping("/report/oneWorker/{workerId}")
    public HttpEntity<?> getCuttingReportsForOneWorker(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset,
            @PathVariable Integer workerId,
            @RequestParam  LocalDate date
    ){
        return cuttingService.getCuttingReportsForOneWorker(search,page,offset,workerId, date);
    }

    @PostMapping
    public HttpEntity<?> create(@RequestBody ReqCutting reqCutting){
        return cuttingService.create(reqCutting);
    }

    @DeleteMapping("/{cuttingId}")
    public HttpEntity<?> delete(@PathVariable Integer cuttingId){
        return cuttingService.delete(cuttingId);
    }

    @PutMapping("/{cuttingId}")
    public HttpEntity<?> edit(@PathVariable Integer cuttingId, @RequestBody ReqCutting reqCutting){
        return cuttingService.edit(cuttingId,reqCutting);
    }
}

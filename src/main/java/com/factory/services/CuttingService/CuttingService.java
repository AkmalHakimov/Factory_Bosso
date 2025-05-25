package com.factory.services.CuttingService;

import com.factory.payload.request.ReqCutting;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;

public interface CuttingService {
    HttpEntity<?> get(String search, Integer page, Integer offset, LocalDate date);

    HttpEntity<?> create(ReqCutting reqCutting);

    HttpEntity<?> delete(Integer cuttingId);

    HttpEntity<?> edit(Integer cuttingId, ReqCutting reqCutting);

    HttpEntity<?> getCuttingReports(String search, Integer page, Integer offset,  LocalDate date);

    HttpEntity<?> getCuttingReportsForOneWorker(String search, Integer page, Integer offset, Integer workerId, LocalDate date);
}

package com.factory.services.ReportService;

import com.factory.payload.request.ReqPieReport;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;

public interface ReportService {
    HttpEntity<?> getSecondaryReports(String search, Integer page, Integer offset, LocalDate date);

    HttpEntity<?> post(ReqPieReport reqPieReport);

    HttpEntity<?> getLeftAmountReport(String search, Integer page, Integer offset, LocalDate date);
}

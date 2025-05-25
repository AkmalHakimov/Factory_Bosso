package com.factory.services.ExcelService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.time.LocalDate;

public interface ExcelService {
    HttpEntity<?> downloadWorkerExcel(HttpServletResponse response, LocalDate date) throws IOException;

    HttpEntity<?> downloadReportExcel(HttpServletResponse response)throws IOException;

    HttpEntity<?> downloadWorkerExcelForList(HttpServletResponse response, LocalDate date, Integer workerId) throws IOException;
}

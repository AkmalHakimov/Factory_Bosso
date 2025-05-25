package com.factory.services.SewingService;

import com.factory.payload.request.ReqSewing;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;

public interface SewingService {
    HttpEntity<?> get(String search, Integer page, Integer offset, LocalDate date);

    HttpEntity<?> create(ReqSewing reqSewing);

    HttpEntity<?> delete(Integer sewingId);

    HttpEntity<?> edit(ReqSewing reqSewing, Integer sewingId);

    HttpEntity<?> getWorkerReports(String search, Integer page, Integer offset, LocalDate date, Integer workerId);

    HttpEntity<?> getReportForOneWorker(String search, Integer page, Integer offset, Integer workerId, LocalDate date);
}

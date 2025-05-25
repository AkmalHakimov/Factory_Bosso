package com.factory.services.IncomeToolService;

import com.factory.payload.request.ReqIncomeTool;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;

public interface IncomeToolService {
    HttpEntity<?> get(String search, Integer toolId, Integer page, Integer offset, LocalDate date);

    HttpEntity<?> post(ReqIncomeTool incomeTool);

    HttpEntity<?> delete(Integer incomeToolId);

    HttpEntity<?> edit(Integer incomeToolId, ReqIncomeTool incomeTool);
}

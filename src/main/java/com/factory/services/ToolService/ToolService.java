package com.factory.services.ToolService;

import com.factory.payload.request.ReqTool;
import org.springframework.http.HttpEntity;

import java.time.LocalDate;

public interface ToolService {

    HttpEntity<?> getTools(String search, Integer toolTypeId, Integer page, Integer offset);

    HttpEntity<?> createTool(ReqTool tool);

    HttpEntity<?> deleteTool(Integer toolId);

    HttpEntity<?> editTool(ReqTool tool, Integer toolId);

    HttpEntity<?> getReports(String search, Integer page, Integer offset, LocalDate date);

    HttpEntity<?> getAllTools();

    HttpEntity<?> getOneTool(Integer toolId);

    HttpEntity<?> getToolPieReport(LocalDate date);

}

package com.factory.services.ToolService;

import com.factory.entity.Tool;
import com.factory.payload.request.ReqTool;
import com.factory.payload.request.ReqToolTypeId;
import com.factory.payload.response.ResTool;
import com.factory.projections.ToolProjection;
import com.factory.projections.ToolReportProjection;
import com.factory.repository.ReportRepo;
import com.factory.repository.ToolRepo;
import com.factory.repository.ToolTypeRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepo toolRepo;
    private final ToolTypeRepo toolTypeRepo;
    private final ObjectMapper objectMapper;
    private final ReportRepo reportRepo;

    @Override
    public HttpEntity<?> getTools(String search, Integer toolTypeId, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        Page<ToolProjection> tools = toolRepo.getTools(pageable, search);
        List<ResTool> toolList = new ArrayList<>();
        tools.forEach(tool -> {
            try {
                String json = tool.getToolTypeId();
                List<ReqToolTypeId> toolTypes = objectMapper.readValue(json, new TypeReference<List<ReqToolTypeId>>() {
                });
                // You can add a setter in the projection or create a DTO to include the parsed toolTypes if needed
                toolList.add(ResTool.builder()
                        .toolTypeId(toolTypes)
                        .marka(tool.getMarka())
                        .name(tool.getName())
                        .size(tool.getSize())
                        .id(tool.getId())
                        .color(tool.getColor())
                        .code(tool.getCode())
                        .dimension(tool.getDimension())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return ResponseEntity.ok(new PageImpl<>(toolList, pageable, tools.getTotalElements()));
    }

    @Override
    public HttpEntity<?> createTool(ReqTool tool) {
        return ResponseEntity.ok(toolRepo.save(Tool.builder()
                .code(tool.getCode())
                .toolTypes(toolTypeRepo.findAllById(tool.getToolTypeIds()))
                .dimension(tool.getDimension())
                .marka(tool.getMarka())
                .size(tool.getSize())
                .color(tool.getColor())
                .name(tool.getName())
                .build()));
    }

    @Override
    public HttpEntity<?> getAllTools() {
        return ResponseEntity.ok(toolRepo.findAll());
    }

    @Override
    public HttpEntity<?> deleteTool(Integer toolId) {
        toolRepo.deleteById(toolId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> getToolPieReport(LocalDate date) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<ToolReportProjection> secondaryReports = reportRepo.getSecondaryReports(pageable, "", date);

        BigDecimal s = BigDecimal.ZERO;

        for (ToolReportProjection secondaryReport : secondaryReports) {
            s = s.add(secondaryReport.getTotalExpensePrice());
        }
        return ResponseEntity.ok(toolRepo.toolPieReport(date, s));
    }

    @Override
    public HttpEntity<?> getOneTool(Integer toolId) {
        return ResponseEntity.ok(toolRepo.findById(toolId));
    }

    @Override
    public HttpEntity<?> editTool(ReqTool tool, Integer toolId) {
        return ResponseEntity.ok(toolRepo.save(Tool.builder()
                .id(toolId)
                .code(tool.getCode())
                .toolTypes(toolTypeRepo.findAllById(tool.getToolTypeIds()))
                .dimension(tool.getDimension())
                .marka(tool.getMarka())
                .name(tool.getName())
                .color(tool.getColor())
                .size(tool.getSize())
                .build()));
    }

    @Override
    public HttpEntity<?> getReports(String search, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(toolRepo.getReports(pageable, search, date));
    }
}


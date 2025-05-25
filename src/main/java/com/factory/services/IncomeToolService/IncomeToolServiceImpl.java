package com.factory.services.IncomeToolService;

import com.factory.entity.IncomeTool;
import com.factory.payload.request.ReqIncomeTool;
import com.factory.repository.IncomeToolRepo;
import com.factory.repository.ToolRepo;
import com.factory.repository.ToolTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class IncomeToolServiceImpl implements IncomeToolService {

    private final IncomeToolRepo incomeToolRepo;
    private final ToolRepo toolRepo;
    private final ToolTypeRepo toolTypeRepo;

    @Override
    public HttpEntity<?> get(String search, Integer toolId, Integer page, Integer offset, LocalDate date) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(incomeToolRepo.getIncomeTools(search, toolId,date,pageable )
        );
    }

    @Override
    public HttpEntity<?> post(ReqIncomeTool incomeTool) {
        return ResponseEntity.ok(incomeToolRepo.save(IncomeTool.builder()
                .tool(toolRepo.findById(incomeTool.getToolId()).orElseThrow())
                .price(incomeTool.getPrice())
                .amount(incomeTool.getAmount())
                .created_at(incomeTool.getCreatedAt())
                .paymentType(incomeTool.getPaymentType())
                .toolType(toolTypeRepo.findById(incomeTool.getToolTypeId()).orElseThrow())
                .build()));
    }

    @Override
    public HttpEntity<?> delete(Integer incomeToolId) {
        incomeToolRepo.deleteById(incomeToolId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> edit(Integer incomeToolId, ReqIncomeTool incomeTool) {
        return ResponseEntity.ok(incomeToolRepo.save(IncomeTool.builder()
                .id(incomeToolId)
                .tool(toolRepo.findById(incomeTool.getToolId()).orElseThrow())
                .price(incomeTool.getPrice())
                .amount(incomeTool.getAmount())
                .created_at(incomeTool.getCreatedAt())
                .paymentType(incomeTool.getPaymentType())
                .toolType(toolTypeRepo.findById(incomeTool.getToolTypeId()).orElseThrow())
                .build()));
    }
}

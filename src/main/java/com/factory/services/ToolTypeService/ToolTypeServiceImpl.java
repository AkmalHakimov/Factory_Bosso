package com.factory.services.ToolTypeService;

import com.factory.entity.ToolType;
import com.factory.repository.ToolTypeRepo;
import com.factory.services.ToolService.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToolTypeServiceImpl implements ToolTypeService {

    private final ToolTypeRepo toolTypeRepo;

    @Override
    public HttpEntity<?> getToolTypes(String search, Integer page, Integer offset) {
        Pageable pageable = PageRequest.of(page - 1, offset);
        return ResponseEntity.ok(toolTypeRepo.getToolTypes(search,pageable));
    }

    @Override
    public HttpEntity<?> getAllToolTypes() {
        return ResponseEntity.ok(toolTypeRepo.findAll());
    }

    @Override
    public HttpEntity<?> createToolType(ToolType toolType) {
        return ResponseEntity.ok(toolTypeRepo.save(ToolType.builder()
                .name(toolType.getName())
                .build()));
    }

    @Override
    public HttpEntity<?> deleteToolType(Integer toolTypeId) {
        toolTypeRepo.deleteById(toolTypeId);
        return ResponseEntity.ok("");
    }

    @Override
    public HttpEntity<?> editToolType(ToolType toolType, Integer toolTypeId) {
        return ResponseEntity.ok(toolTypeRepo.save(ToolType.builder()
                .id(toolTypeId)
                .name(toolType.getName())
                .build()));
    }
}

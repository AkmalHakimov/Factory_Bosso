package com.factory.controller;

import com.factory.entity.Tool;
import com.factory.entity.ToolType;
import com.factory.services.ToolService.ToolService;
import com.factory.services.ToolTypeService.ToolTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/toolType")
@RequiredArgsConstructor
public class ToolTypeController {

    private final ToolTypeService toolTypeService;

    @GetMapping
    public HttpEntity<?> getToolTypes(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer offset
    ) {
        return toolTypeService.getToolTypes(search, page, offset);
    }


    @GetMapping("/all")
    public HttpEntity<?> getAllToolTypes(
    ) {
        return toolTypeService.getAllToolTypes();
    }

    @PostMapping
    public HttpEntity<?> createToolType(@RequestBody ToolType toolType) {
        return toolTypeService.createToolType(toolType);
    }

    @DeleteMapping("/{toolTypeId}")
    public HttpEntity<?> deleteToolType(@PathVariable Integer toolTypeId) {
        return toolTypeService.deleteToolType(toolTypeId);
    }

    @PutMapping("/{toolTypeId}")
    public HttpEntity<?> editToolType(@PathVariable Integer toolTypeId, @RequestBody ToolType toolType) {
        return toolTypeService.editToolType(toolType, toolTypeId);
    }
}

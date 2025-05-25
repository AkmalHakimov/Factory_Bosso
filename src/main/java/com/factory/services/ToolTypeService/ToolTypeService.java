package com.factory.services.ToolTypeService;

import com.factory.entity.ToolType;
import org.springframework.http.HttpEntity;

public interface ToolTypeService {

    HttpEntity<?> createToolType(ToolType toolType);

    HttpEntity<?> deleteToolType(Integer toolTypeId);

    HttpEntity<?> editToolType(ToolType toolType, Integer toolTypeId);

    HttpEntity<?> getToolTypes(String search, Integer page, Integer offset);

    HttpEntity<?> getAllToolTypes();
}

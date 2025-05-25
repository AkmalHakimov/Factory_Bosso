package com.factory.projections;

import com.factory.payload.request.ReqToolTypeId;

import java.util.List;

public interface ToolProjection {
    Integer getId();

    Integer getCode();

    String getDimension();

    String getSize();

    String getColor();

    String getMarka();

    String getName();

//    String getToolTypeName();

    String getToolTypeId();
}

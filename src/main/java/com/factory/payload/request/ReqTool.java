package com.factory.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqTool {

    private Integer code;

    private String marka;

    private String name;

    private String size;

    private String color;

    private String dimension;

    private List<Integer> toolTypeIds;
}

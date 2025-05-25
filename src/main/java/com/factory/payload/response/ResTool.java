package com.factory.payload.response;

import com.factory.payload.request.ReqToolTypeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResTool {

    private Integer id;

    private Integer code;

    private String dimension;

    private String size;
    private String color;
    private String marka;
    private String name;
    private List<ReqToolTypeId> toolTypeId;
}

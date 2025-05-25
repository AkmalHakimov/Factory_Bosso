package com.factory.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqWorker {
    private String firstName;
    private String lastName;

    private Boolean sacked;
    private String role;
}

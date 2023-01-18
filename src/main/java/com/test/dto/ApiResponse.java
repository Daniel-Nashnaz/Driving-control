package com.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Instant timeUtc;
    // private Boolean success;
    private String message;
    private String path;

}

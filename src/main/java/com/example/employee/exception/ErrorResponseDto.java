package com.example.employee.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ErrorResponseDto {
    private int status;
    private String message;
    private long timestamp;
}


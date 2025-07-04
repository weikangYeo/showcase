package com.wk.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorDto {
    /**
     * For lang pack impl (backlog)
     */
    private String code;
    private String message;
    private int status;
    private String reason;
    private Date timestamp;
}

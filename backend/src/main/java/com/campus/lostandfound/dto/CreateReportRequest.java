package com.campus.lostandfound.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateReportRequest {
    @NotBlank
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

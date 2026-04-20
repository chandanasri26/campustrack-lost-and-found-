package com.campus.lostandfound.dto;

import com.campus.lostandfound.model.Report;

import java.time.LocalDateTime;

public record ReportDto(
        String id,
        String itemId,
        String reporterId,
        String reason,
        String status,
        String adminComment,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {
    public static ReportDto from(Report report) {
        return new ReportDto(
                report.getId(),
                report.getItemId(),
                report.getReporterId(),
                report.getReason(),
                report.getStatus(),
                report.getAdminComment(),
                report.getCreatedAt(),
                report.getResolvedAt()
        );
    }
}

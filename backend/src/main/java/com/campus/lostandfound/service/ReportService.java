package com.campus.lostandfound.service;

import com.campus.lostandfound.dto.CreateReportRequest;
import com.campus.lostandfound.dto.ReportDto;
import com.campus.lostandfound.model.Report;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.repository.ItemRepository;
import com.campus.lostandfound.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ItemRepository itemRepository;

    public ReportDto fileReport(String itemId, CreateReportRequest request, User currentUser) {
        if (!itemRepository.existsById(itemId)) {
            throw new IllegalArgumentException("Item not found");
        }

        Report report = new Report();
        report.setItemId(itemId);
        report.setReporterId(currentUser.getId());
        report.setReason(request.getReason());
        report.setStatus("open");
        return ReportDto.from(reportRepository.save(report));
    }

    public List<ReportDto> getAllReports() {
        return reportRepository.findByStatusOrderByCreatedAtDesc("open")
                .stream().map(ReportDto::from).collect(Collectors.toList());
    }

    public ReportDto resolveReport(String id, String adminComment) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
        report.setStatus("resolved");
        report.setAdminComment(adminComment);
        report.setResolvedAt(LocalDateTime.now());
        return ReportDto.from(reportRepository.save(report));
    }
}

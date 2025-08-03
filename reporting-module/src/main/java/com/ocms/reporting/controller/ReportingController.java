package com.ocms.reporting.controller;

import com.ocms.common.dto.ApiResponse;
import com.ocms.reporting.dto.AssignmentPerformanceReport;
import com.ocms.reporting.dto.CourseCompletionReport;
import com.ocms.reporting.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    @Autowired
    private ReportingService reportingService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDetailedReports() {
        Map<String, Object> reports = reportingService.getDetailedReports();
        return ResponseEntity.ok(ApiResponse.success("Detailed reports retrieved successfully", reports));
    }

    @GetMapping("/course-completion")
    public ResponseEntity<ApiResponse<List<CourseCompletionReport>>> getCourseCompletionReports() {
        List<CourseCompletionReport> reports = reportingService.getCourseCompletionReports();
        return ResponseEntity.ok(ApiResponse.success("Course completion reports retrieved successfully", reports));
    }

    @GetMapping("/assignment-performance")
    public ResponseEntity<ApiResponse<List<AssignmentPerformanceReport>>> getAssignmentPerformanceReports() {
        List<AssignmentPerformanceReport> reports = reportingService.getAssignmentPerformanceReports();
        return ResponseEntity.ok(ApiResponse.success("Assignment performance reports retrieved successfully", reports));
    }

    @GetMapping("/submissions-by-date")
    public ResponseEntity<ApiResponse<Map<LocalDate, Integer>>> getSubmissionCountByDate() {
        Map<LocalDate, Integer> submissionCounts = reportingService.getSubmissionCountByDate();
        return ResponseEntity.ok(ApiResponse.success("Submission counts by date retrieved successfully", submissionCounts));
    }

    @GetMapping("/overall-statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOverallStatistics() {
        Map<String, Object> statistics = reportingService.getOverallStatistics();
        return ResponseEntity.ok(ApiResponse.success("Overall statistics retrieved successfully", statistics));
    }
}
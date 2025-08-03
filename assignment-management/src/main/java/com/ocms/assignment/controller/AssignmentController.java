package com.ocms.assignment.controller;

import com.ocms.assignment.dto.AssignmentDto;
import com.ocms.assignment.dto.GradeSubmissionDto;
import com.ocms.assignment.dto.SubmissionDto;
import com.ocms.assignment.entity.Assignment;
import com.ocms.assignment.entity.Submission;
import com.ocms.assignment.service.AssignmentService;
import com.ocms.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Assignment>> createAssignment(@Valid @RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = assignmentService.createAssignment(assignmentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Assignment created successfully", assignment));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Assignment>>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(ApiResponse.success("Assignments retrieved successfully", assignments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Assignment>> getAssignmentById(@PathVariable Long id) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(ApiResponse.success("Assignment retrieved successfully", assignment));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<Assignment>>> getAssignmentsByCourse(@PathVariable Long courseId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success("Course assignments retrieved successfully", assignments));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Assignment>> updateAssignment(@PathVariable Long id, @Valid @RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = assignmentService.updateAssignment(id, assignmentDto);
        return ResponseEntity.ok(ApiResponse.success("Assignment updated successfully", assignment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok(ApiResponse.success("Assignment deleted successfully", null));
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<Submission>> submitAssignment(@PathVariable Long id, @Valid @RequestBody SubmissionDto submissionDto) {
        Submission submission = assignmentService.submitAssignment(id, submissionDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Assignment submitted successfully", submission));
    }

    @GetMapping("/{id}/submissions")
    public ResponseEntity<ApiResponse<List<Submission>>> getSubmissionsByAssignment(@PathVariable Long id) {
        List<Submission> submissions = assignmentService.getSubmissionsByAssignment(id);
        return ResponseEntity.ok(ApiResponse.success("Assignment submissions retrieved successfully", submissions));
    }

    @GetMapping("/submissions/user/{userId}")
    public ResponseEntity<ApiResponse<List<Submission>>> getSubmissionsByUser(@PathVariable Long userId) {
        List<Submission> submissions = assignmentService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User submissions retrieved successfully", submissions));
    }

    @PutMapping("/submissions/{submissionId}/grade")
    public ResponseEntity<ApiResponse<Submission>> gradeSubmission(@PathVariable Long submissionId, @Valid @RequestBody GradeSubmissionDto gradeDto) {
        Submission submission = assignmentService.gradeSubmission(submissionId, gradeDto);
        return ResponseEntity.ok(ApiResponse.success("Submission graded successfully", submission));
    }

    @GetMapping("/submissions/ordered")
    public ResponseEntity<ApiResponse<List<Submission>>> getSubmissionsOrderedByDate() {
        List<Submission> submissions = assignmentService.getSubmissionsOrderedByDate();
        return ResponseEntity.ok(ApiResponse.success("Submissions retrieved in order", submissions));
    }
}
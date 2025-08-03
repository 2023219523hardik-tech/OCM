package com.ocms.assignment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission implements Comparable<Submission> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long submissionId;

    @NotNull(message = "Assignment ID is required")
    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull(message = "Submitted on date is required")
    @Column(name = "submitted_on", nullable = false)
    private LocalDateTime submittedOn;

    @Column(name = "score")
    private Double score;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    // Constructors
    public Submission() {}

    public Submission(Long assignmentId, Long userId, String content, LocalDateTime submittedOn) {
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.content = content;
        this.submittedOn = submittedOn;
    }

    // Implement Comparable for PriorityQueue sorting by submitted date
    @Override
    public int compareTo(Submission other) {
        return this.submittedOn.compareTo(other.submittedOn);
    }

    // Getters and Setters
    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(LocalDateTime submittedOn) {
        this.submittedOn = submittedOn;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
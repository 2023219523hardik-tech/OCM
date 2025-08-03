package com.ocms.reporting.dto;

public class AssignmentPerformanceReport {
    private Long assignmentId;
    private String assignmentTitle;
    private Long totalSubmissions;
    private Double averageScore;
    private Double maxScore;
    private Double minScore;
    private Double maxPossibleScore;

    // Constructors
    public AssignmentPerformanceReport() {}

    public AssignmentPerformanceReport(Long assignmentId, String assignmentTitle, Long totalSubmissions, 
                                     Double averageScore, Double maxScore, Double minScore, Double maxPossibleScore) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.totalSubmissions = totalSubmissions;
        this.averageScore = averageScore;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.maxPossibleScore = maxPossibleScore;
    }

    // Getters and Setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public Long getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(Long totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public Double getMinScore() {
        return minScore;
    }

    public void setMinScore(Double minScore) {
        this.minScore = minScore;
    }

    public Double getMaxPossibleScore() {
        return maxPossibleScore;
    }

    public void setMaxPossibleScore(Double maxPossibleScore) {
        this.maxPossibleScore = maxPossibleScore;
    }
}
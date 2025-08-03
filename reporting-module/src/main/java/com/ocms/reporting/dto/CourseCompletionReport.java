package com.ocms.reporting.dto;

public class CourseCompletionReport {
    private Long courseId;
    private String courseTitle;
    private Long totalStudents;
    private Long completedStudents;
    private Double completionRate;

    // Constructors
    public CourseCompletionReport() {}

    public CourseCompletionReport(Long courseId, String courseTitle, Long totalStudents, Long completedStudents) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.totalStudents = totalStudents;
        this.completedStudents = completedStudents;
        this.completionRate = totalStudents > 0 ? (completedStudents.doubleValue() / totalStudents.doubleValue()) * 100 : 0.0;
    }

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Long getCompletedStudents() {
        return completedStudents;
    }

    public void setCompletedStudents(Long completedStudents) {
        this.completedStudents = completedStudents;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }
}
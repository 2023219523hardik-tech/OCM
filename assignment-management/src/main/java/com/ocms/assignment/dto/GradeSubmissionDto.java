package com.ocms.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class GradeSubmissionDto {
    @NotNull(message = "Score is required")
    @PositiveOrZero(message = "Score must be positive or zero")
    private Double score;

    private String feedback;

    // Constructors
    public GradeSubmissionDto() {}

    public GradeSubmissionDto(Double score, String feedback) {
        this.score = score;
        this.feedback = feedback;
    }

    // Getters and Setters
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
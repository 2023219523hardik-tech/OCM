package com.ocms.assignment.dto;

import jakarta.validation.constraints.NotNull;

public class SubmissionDto {
    @NotNull(message = "User ID is required")
    private Long userId;

    private String content;

    // Constructors
    public SubmissionDto() {}

    public SubmissionDto(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    // Getters and Setters
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
}
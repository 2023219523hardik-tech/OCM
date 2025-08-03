package com.ocms.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseDto {
    @NotNull(message = "Instructor ID is required")
    private Long instructorId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    // Constructors
    public CourseDto() {}

    public CourseDto(Long instructorId, String title, String description) {
        this.instructorId = instructorId;
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
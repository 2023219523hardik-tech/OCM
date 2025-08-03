package com.ocms.course.dto;

import jakarta.validation.constraints.NotBlank;

public class ModuleDto {
    @NotBlank(message = "Module title is required")
    private String moduleTitle;

    private String content;

    // Constructors
    public ModuleDto() {}

    public ModuleDto(String moduleTitle, String content) {
        this.moduleTitle = moduleTitle;
        this.content = content;
    }

    // Getters and Setters
    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
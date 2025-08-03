package com.ocms.course.controller;

import com.ocms.common.dto.ApiResponse;
import com.ocms.course.dto.CourseDto;
import com.ocms.course.dto.ModuleDto;
import com.ocms.course.entity.Course;
import com.ocms.course.entity.Module;
import com.ocms.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@Valid @RequestBody CourseDto courseDto) {
        Course course = courseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Course created successfully", course));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(ApiResponse.success("Courses retrieved successfully", courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(ApiResponse.success("Course retrieved successfully", course));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<List<Course>>> getCoursesByInstructor(@PathVariable Long instructorId) {
        List<Course> courses = courseService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(ApiResponse.success("Instructor courses retrieved successfully", courses));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseDto courseDto) {
        Course course = courseService.updateCourse(id, courseDto);
        return ResponseEntity.ok(ApiResponse.success("Course updated successfully", course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(ApiResponse.success("Course deleted successfully", null));
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<ApiResponse<Module>> addModuleToCourse(@PathVariable Long id, @Valid @RequestBody ModuleDto moduleDto) {
        Module module = courseService.addModuleToCourse(id, moduleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Module added to course successfully", module));
    }

    @GetMapping("/{id}/modules")
    public ResponseEntity<ApiResponse<List<Module>>> getCourseModules(@PathVariable Long id) {
        List<Module> modules = courseService.getCourseModules(id);
        return ResponseEntity.ok(ApiResponse.success("Course modules retrieved successfully", modules));
    }

    @PutMapping("/modules/{moduleId}")
    public ResponseEntity<ApiResponse<Module>> updateModule(@PathVariable Long moduleId, @Valid @RequestBody ModuleDto moduleDto) {
        Module module = courseService.updateModule(moduleId, moduleDto);
        return ResponseEntity.ok(ApiResponse.success("Module updated successfully", module));
    }

    @DeleteMapping("/modules/{moduleId}")
    public ResponseEntity<ApiResponse<Void>> deleteModule(@PathVariable Long moduleId) {
        courseService.deleteModule(moduleId);
        return ResponseEntity.ok(ApiResponse.success("Module deleted successfully", null));
    }
}
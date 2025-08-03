package com.ocms.course.service;

import com.ocms.common.exception.CustomExceptions;
import com.ocms.course.dto.CourseDto;
import com.ocms.course.dto.ModuleDto;
import com.ocms.course.entity.Course;
import com.ocms.course.entity.Module;
import com.ocms.course.repository.CourseRepository;
import com.ocms.course.repository.ModuleRepository;
import com.ocms.course.util.ModuleLinkedList;
import com.ocms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private UserService userService;

    // HashMap to store course modules using LinkedList
    private final Map<Long, ModuleLinkedList> courseModules = new HashMap<>();

    @Transactional
    public Course createCourse(CourseDto courseDto) {
        // Verify instructor exists and has instructor role
        if (!userService.isInstructor(courseDto.getInstructorId())) {
            throw new CustomExceptions.BadRequestException("Only instructors can create courses");
        }

        Course course = new Course(
            courseDto.getInstructorId(),
            courseDto.getTitle(),
            courseDto.getDescription()
        );

        Course savedCourse = courseRepository.save(course);
        
        // Initialize empty LinkedList for course modules
        courseModules.put(savedCourse.getCourseId(), new ModuleLinkedList());
        
        return savedCourse;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Course not found with id: " + courseId));
    }

    public List<Course> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

    @Transactional
    public Course updateCourse(Long courseId, CourseDto courseDto) {
        Course course = getCourseById(courseId);
        
        // Verify instructor owns the course or is updating their own course
        if (!course.getInstructorId().equals(courseDto.getInstructorId())) {
            throw new CustomExceptions.BadRequestException("Instructor can only update their own courses");
        }

        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = getCourseById(courseId);
        
        // Delete all modules first
        List<Module> modules = moduleRepository.findByCourseIdOrderByOrderIndex(courseId);
        moduleRepository.deleteAll(modules);
        
        // Remove from HashMap
        courseModules.remove(courseId);
        
        // Delete course
        courseRepository.delete(course);
    }

    @Transactional
    public Module addModuleToCourse(Long courseId, ModuleDto moduleDto) {
        Course course = getCourseById(courseId);
        
        // Get current max order index
        Integer maxIndex = moduleRepository.findMaxOrderIndexByCourseId(courseId);
        int nextIndex = (maxIndex == null) ? 1 : maxIndex + 1;
        
        Module module = new Module(
            courseId,
            moduleDto.getModuleTitle(),
            moduleDto.getContent(),
            nextIndex
        );
        
        Module savedModule = moduleRepository.save(module);
        
        // Add to LinkedList
        ModuleLinkedList moduleList = courseModules.computeIfAbsent(courseId, k -> new ModuleLinkedList());
        moduleList.addModule(savedModule);
        
        return savedModule;
    }

    public List<Module> getCourseModules(Long courseId) {
        getCourseById(courseId); // Verify course exists
        
        // Load modules from database if not in HashMap
        if (!courseModules.containsKey(courseId)) {
            List<Module> modules = moduleRepository.findByCourseIdOrderByOrderIndex(courseId);
            courseModules.put(courseId, new ModuleLinkedList(modules));
        }
        
        return courseModules.get(courseId).getAllModules();
    }

    @Transactional
    public Module updateModule(Long moduleId, ModuleDto moduleDto) {
        Module module = moduleRepository.findById(moduleId)
            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Module not found with id: " + moduleId));
        
        module.setModuleTitle(moduleDto.getModuleTitle());
        module.setContent(moduleDto.getContent());
        
        Module savedModule = moduleRepository.save(module);
        
        // Update in LinkedList
        ModuleLinkedList moduleList = courseModules.get(module.getCourseId());
        if (moduleList != null) {
            // Refresh the LinkedList
            List<Module> modules = moduleRepository.findByCourseIdOrderByOrderIndex(module.getCourseId());
            courseModules.put(module.getCourseId(), new ModuleLinkedList(modules));
        }
        
        return savedModule;
    }

    @Transactional
    public void deleteModule(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Module not found with id: " + moduleId));
        
        Long courseId = module.getCourseId();
        
        moduleRepository.delete(module);
        
        // Update LinkedList and reorder
        List<Module> modules = moduleRepository.findByCourseIdOrderByOrderIndex(courseId);
        ModuleLinkedList moduleList = new ModuleLinkedList(modules);
        moduleList.updateOrderIndices();
        
        // Save updated order indices
        for (Module m : moduleList.getAllModules()) {
            moduleRepository.save(m);
        }
        
        courseModules.put(courseId, moduleList);
    }

    public Map<Long, ModuleLinkedList> getAllCourseModules() {
        return new HashMap<>(courseModules);
    }
}
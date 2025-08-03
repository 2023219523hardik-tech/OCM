package com.ocms.assignment.service;

import com.ocms.assignment.dto.AssignmentDto;
import com.ocms.assignment.dto.GradeSubmissionDto;
import com.ocms.assignment.dto.SubmissionDto;
import com.ocms.assignment.entity.Assignment;
import com.ocms.assignment.entity.Submission;
import com.ocms.assignment.repository.AssignmentRepository;
import com.ocms.assignment.repository.SubmissionRepository;
import com.ocms.common.exception.CustomExceptions;
import com.ocms.course.service.CourseService;
import com.ocms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    // PriorityQueue to manage submissions based on submitted_on date
    private final PriorityQueue<Submission> submissionQueue = new PriorityQueue<>();

    @Transactional
    public Assignment createAssignment(AssignmentDto assignmentDto) {
        // Verify course exists
        courseService.getCourseById(assignmentDto.getCourseId());

        Assignment assignment = new Assignment(
            assignmentDto.getCourseId(),
            assignmentDto.getTitle(),
            assignmentDto.getDescription(),
            assignmentDto.getDueDate(),
            assignmentDto.getMaxScore()
        );

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(Long assignmentId) {
        return assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Assignment not found with id: " + assignmentId));
    }

    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        courseService.getCourseById(courseId); // Verify course exists
        return assignmentRepository.findByCourseId(courseId);
    }

    @Transactional
    public Assignment updateAssignment(Long assignmentId, AssignmentDto assignmentDto) {
        Assignment assignment = getAssignmentById(assignmentId);
        
        assignment.setTitle(assignmentDto.getTitle());
        assignment.setDescription(assignmentDto.getDescription());
        assignment.setDueDate(assignmentDto.getDueDate());
        assignment.setMaxScore(assignmentDto.getMaxScore());

        return assignmentRepository.save(assignment);
    }

    @Transactional
    public void deleteAssignment(Long assignmentId) {
        Assignment assignment = getAssignmentById(assignmentId);
        
        // Delete all submissions first
        List<Submission> submissions = submissionRepository.findByAssignmentId(assignmentId);
        submissionRepository.deleteAll(submissions);
        
        // Remove from priority queue
        submissionQueue.removeAll(submissions);
        
        assignmentRepository.delete(assignment);
    }

    @Transactional
    public Submission submitAssignment(Long assignmentId, SubmissionDto submissionDto) {
        Assignment assignment = getAssignmentById(assignmentId);
        
        // Verify user exists and is a student
        if (!userService.isStudent(submissionDto.getUserId())) {
            throw new CustomExceptions.BadRequestException("Only students can submit assignments");
        }

        // Check if user already submitted this assignment
        Optional<Submission> existingSubmission = submissionRepository
            .findByAssignmentIdAndUserId(assignmentId, submissionDto.getUserId());
        
        if (existingSubmission.isPresent()) {
            throw new CustomExceptions.DuplicateResourceException("Assignment already submitted by this user");
        }

        // Check if assignment is overdue
        LocalDateTime now = LocalDateTime.now();
        if (now.toLocalDate().isAfter(assignment.getDueDate())) {
            throw new CustomExceptions.BadRequestException("Assignment submission is overdue");
        }

        Submission submission = new Submission(
            assignmentId,
            submissionDto.getUserId(),
            submissionDto.getContent(),
            now
        );

        Submission savedSubmission = submissionRepository.save(submission);
        
        // Add to priority queue
        submissionQueue.offer(savedSubmission);
        
        return savedSubmission;
    }

    public List<Submission> getSubmissionsByAssignment(Long assignmentId) {
        getAssignmentById(assignmentId); // Verify assignment exists
        return submissionRepository.findByAssignmentId(assignmentId);
    }

    public List<Submission> getSubmissionsByUser(Long userId) {
        userService.getUserById(userId); // Verify user exists
        return submissionRepository.findByUserId(userId);
    }

    @Transactional
    public Submission gradeSubmission(Long submissionId, GradeSubmissionDto gradeDto) {
        Submission submission = submissionRepository.findById(submissionId)
            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Submission not found with id: " + submissionId));

        Assignment assignment = getAssignmentById(submission.getAssignmentId());
        
        // Validate score doesn't exceed max score
        if (gradeDto.getScore() > assignment.getMaxScore()) {
            throw new CustomExceptions.ValidationException("Score cannot exceed maximum score of " + assignment.getMaxScore());
        }

        submission.setScore(gradeDto.getScore());
        submission.setFeedback(gradeDto.getFeedback());

        Submission savedSubmission = submissionRepository.save(submission);
        
        // Update in priority queue
        submissionQueue.remove(submission);
        submissionQueue.offer(savedSubmission);
        
        return savedSubmission;
    }

    public List<Submission> getSubmissionsOrderedByDate() {
        // Refresh priority queue from database
        submissionQueue.clear();
        List<Submission> allSubmissions = submissionRepository.findAllOrderBySubmittedOn();
        submissionQueue.addAll(allSubmissions);
        
        // Return ordered list from priority queue
        return new ArrayList<>(submissionQueue);
    }

    public PriorityQueue<Submission> getSubmissionQueue() {
        // Refresh queue if empty
        if (submissionQueue.isEmpty()) {
            List<Submission> allSubmissions = submissionRepository.findAllOrderBySubmittedOn();
            submissionQueue.addAll(allSubmissions);
        }
        return new PriorityQueue<>(submissionQueue);
    }
}
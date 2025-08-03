package com.ocms.reporting.service;

import com.ocms.assignment.entity.Assignment;
import com.ocms.assignment.entity.Submission;
import com.ocms.assignment.repository.AssignmentRepository;
import com.ocms.assignment.repository.SubmissionRepository;
import com.ocms.course.entity.Course;
import com.ocms.course.repository.CourseRepository;
import com.ocms.reporting.dto.AssignmentPerformanceReport;
import com.ocms.reporting.dto.CourseCompletionReport;
import com.ocms.user.entity.User;
import com.ocms.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportingService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    // TreeMap to store submissions sorted by date
    private final TreeMap<LocalDate, List<Submission>> submissionsByDate = new TreeMap<>();

    public List<CourseCompletionReport> getCourseCompletionReports() {
        List<Course> courses = courseRepository.findAll();
        List<CourseCompletionReport> reports = new ArrayList<>();

        for (Course course : courses) {
            // Get all assignments for this course
            List<Assignment> assignments = assignmentRepository.findByCourseId(course.getCourseId());
            
            if (assignments.isEmpty()) {
                reports.add(new CourseCompletionReport(course.getCourseId(), course.getTitle(), 0L, 0L));
                continue;
            }

            // Get all students
            List<User> students = userRepository.findAll().stream()
                .filter(user -> "STUDENT".equals(user.getRole()))
                .collect(Collectors.toList());

            long totalStudents = students.size();
            long completedStudents = 0;

            // Check completion for each student
            for (User student : students) {
                boolean hasCompletedAllAssignments = true;
                
                for (Assignment assignment : assignments) {
                    Optional<Submission> submission = submissionRepository
                        .findByAssignmentIdAndUserId(assignment.getAssignmentId(), student.getUserId());
                    
                    if (submission.isEmpty() || submission.get().getScore() == null) {
                        hasCompletedAllAssignments = false;
                        break;
                    }
                }
                
                if (hasCompletedAllAssignments) {
                    completedStudents++;
                }
            }

            reports.add(new CourseCompletionReport(course.getCourseId(), course.getTitle(), totalStudents, completedStudents));
        }

        return reports;
    }

    public List<AssignmentPerformanceReport> getAssignmentPerformanceReports() {
        List<Assignment> assignments = assignmentRepository.findAll();
        List<AssignmentPerformanceReport> reports = new ArrayList<>();

        for (Assignment assignment : assignments) {
            List<Submission> submissions = submissionRepository.findByAssignmentId(assignment.getAssignmentId());
            
            // Filter submissions with scores
            List<Submission> gradedSubmissions = submissions.stream()
                .filter(s -> s.getScore() != null)
                .collect(Collectors.toList());

            if (gradedSubmissions.isEmpty()) {
                reports.add(new AssignmentPerformanceReport(
                    assignment.getAssignmentId(),
                    assignment.getTitle(),
                    0L,
                    0.0,
                    0.0,
                    0.0,
                    assignment.getMaxScore()
                ));
                continue;
            }

            // Calculate statistics
            DoubleSummaryStatistics stats = gradedSubmissions.stream()
                .mapToDouble(Submission::getScore)
                .summaryStatistics();

            reports.add(new AssignmentPerformanceReport(
                assignment.getAssignmentId(),
                assignment.getTitle(),
                (long) gradedSubmissions.size(),
                stats.getAverage(),
                stats.getMax(),
                stats.getMin(),
                assignment.getMaxScore()
            ));
        }

        return reports;
    }

    public TreeMap<LocalDate, List<Submission>> getSubmissionsByDateSorted() {
        // Clear and refresh the TreeMap
        submissionsByDate.clear();
        
        List<Submission> allSubmissions = submissionRepository.findAll();
        
        // Group submissions by date using TreeMap for automatic sorting
        for (Submission submission : allSubmissions) {
            LocalDate submissionDate = submission.getSubmittedOn().toLocalDate();
            submissionsByDate.computeIfAbsent(submissionDate, k -> new ArrayList<>()).add(submission);
        }
        
        return new TreeMap<>(submissionsByDate);
    }

    public Map<String, Object> getOverallStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // User statistics
        List<User> allUsers = userRepository.findAll();
        long totalStudents = allUsers.stream().filter(u -> "STUDENT".equals(u.getRole())).count();
        long totalInstructors = allUsers.stream().filter(u -> "INSTRUCTOR".equals(u.getRole())).count();
        
        // Course statistics
        long totalCourses = courseRepository.count();
        
        // Assignment statistics
        long totalAssignments = assignmentRepository.count();
        
        // Submission statistics
        long totalSubmissions = submissionRepository.count();
        long gradedSubmissions = submissionRepository.findAll().stream()
            .filter(s -> s.getScore() != null)
            .count();
        
        stats.put("totalStudents", totalStudents);
        stats.put("totalInstructors", totalInstructors);
        stats.put("totalCourses", totalCourses);
        stats.put("totalAssignments", totalAssignments);
        stats.put("totalSubmissions", totalSubmissions);
        stats.put("gradedSubmissions", gradedSubmissions);
        stats.put("pendingGrading", totalSubmissions - gradedSubmissions);
        
        return stats;
    }

    public Map<String, Object> getDetailedReports() {
        Map<String, Object> reports = new HashMap<>();
        
        reports.put("courseCompletionReports", getCourseCompletionReports());
        reports.put("assignmentPerformanceReports", getAssignmentPerformanceReports());
        reports.put("submissionsByDate", getSubmissionsByDateSorted());
        reports.put("overallStatistics", getOverallStatistics());
        
        return reports;
    }

    public Map<LocalDate, Integer> getSubmissionCountByDate() {
        TreeMap<LocalDate, List<Submission>> submissionsByDate = getSubmissionsByDateSorted();
        Map<LocalDate, Integer> submissionCounts = new TreeMap<>();
        
        for (Map.Entry<LocalDate, List<Submission>> entry : submissionsByDate.entrySet()) {
            submissionCounts.put(entry.getKey(), entry.getValue().size());
        }
        
        return submissionCounts;
    }
}
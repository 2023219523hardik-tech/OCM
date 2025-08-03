package com.ocms.assignment.repository;

import com.ocms.assignment.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByAssignmentId(Long assignmentId);
    List<Submission> findByUserId(Long userId);
    Optional<Submission> findByAssignmentIdAndUserId(Long assignmentId, Long userId);
    
    @Query("SELECT s FROM Submission s ORDER BY s.submittedOn ASC")
    List<Submission> findAllOrderBySubmittedOn();
}
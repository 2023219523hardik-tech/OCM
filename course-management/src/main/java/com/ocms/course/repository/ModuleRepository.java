package com.ocms.course.repository;

import com.ocms.course.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByCourseIdOrderByOrderIndex(Long courseId);
    
    @Query("SELECT MAX(m.orderIndex) FROM Module m WHERE m.courseId = :courseId")
    Integer findMaxOrderIndexByCourseId(Long courseId);
}
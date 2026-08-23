package com.shubham.student_management.repository;

import com.shubham.student_management.entity.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {

    boolean existsByCourseCodeIgnoreCase(String courseCode);

    Page<Courses> findByActiveTrue(Pageable pageable);
}

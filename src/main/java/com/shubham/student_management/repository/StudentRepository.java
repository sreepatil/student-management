package com.shubham.student_management.repository;

import com.shubham.student_management.dto.StudentDto;
import com.shubham.student_management.entity.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Students, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Page<Students> findByActive(boolean active, Pageable pageable);

    boolean existsByEmailIgnoreCaseAndIdNot(String email,Long id);

    List<Students> findByActiveTrue();


}

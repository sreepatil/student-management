package com.shubham.student_management.repository;

import com.shubham.student_management.entity.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Page<Students> findByActive(boolean active, Pageable pageable);

    boolean existsByEmailIgnoreCaseAndIdNot(String email,Long id);

    List<Students> findByActiveTrue();

	@Query(value = """
        select distinct s
        from Students s
        join s.enrollment e
        """,
			countQuery = """
        select count(distinct s)
        from Students s
        join s.enrollment e
        """)
	Page<Students> findEnrolledStudents(Pageable pageable);

	@Query("""
			select s
			from Students s
			join fetch s.enrollment e
			join fetch e.course c
			where s.id = :id
			""")
	Optional<Students> findEnrolledStudentCourseDetails(@Param("id") Long id);
}

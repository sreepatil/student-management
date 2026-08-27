package com.shubham.student_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    @Column(nullable = false)
    private LocalDateTime enrolledDate = LocalDateTime.now();
}

package com.shubham.student_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record StudentDto(
                         Long id,
                         @NotBlank(message = "firstname is required !!")
                         String firstName,

                         @NotBlank(message = "lastName is required !!")
                         String lastName,

                         @NotBlank(message = "email is required !!")
                         @Email(message = "enter a valid email")
                         String email,

                         @NotBlank(message = "PhoneNumber is required !!")
                         String phoneNumber,

                         @NotBlank(message = "firstname is required !!")
                         String address,

                         boolean active
                         ) {
}

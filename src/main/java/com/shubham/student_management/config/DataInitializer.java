package com.shubham.student_management.config;

import com.shubham.student_management.entity.User;
import com.shubham.student_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            System.out.println("========== DataInitializer running ==========");

            if (!userRepository.existsByUsername("Admin")) {

                User user = new User();
                user.setUsername("Admin");
                user.setPassword(passwordEncoder.encode("admin@123"));
                user.setActive(true);

                userRepository.save(user);

                System.out.println("========== Admin user created ==========");
            } else {
                System.out.println("========== Admin already exists ==========");
            }
        };
    }
}

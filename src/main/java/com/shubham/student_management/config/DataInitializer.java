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

            if (!userRepository.existsByUsername("Admin")) {

                User user = new User();
                user.setUsername("Admin");
                user.setPassword(passwordEncoder.encode("aaa"));
                user.setActive(true);

                userRepository.save(user);
            }
        };
    }
}

package com.animeSite;

import com.animeSite.persist.User;
import com.animeSite.constant.Role;
import com.animeSite.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class AnimeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimeBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner printUrls() {
        return args -> {
            System.out.println("===========================================");
            System.out.println("Anime Backend API is running!");
            System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
            System.out.println("OpenAPI Docs: http://localhost:8080/api-docs");
            System.out.println("===========================================");
        };
    }

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@animesite.com")) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@animesite.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ROLE_ADMIN);
                admin.setVerified(true);
                userRepository.save(admin);
                System.out.println("Default admin created: admin@animesite.com / admin123");
            }
        };
    }
}

package com.animeSite.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(
            java.security.Principal.class != null ?
            org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication() != null ?
            org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName() : "system" : "system"
        );
    }
}

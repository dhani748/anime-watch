package com.animeSite.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async void sendVerificationEmail(String to, String token);
    @Async void sendPasswordResetEmail(String to, String token);
}

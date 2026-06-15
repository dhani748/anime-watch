package com.animeSite.service;

import com.animeSite.model.ChangePasswordRequest;
import com.animeSite.model.UpdateProfileRequest;
import com.animeSite.persist.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UserService {
    User getProfile(UUID userId);
    User updateProfile(UUID userId, UpdateProfileRequest request);
    void changePassword(UUID userId, ChangePasswordRequest request);
    void deleteAccount(UUID userId);
}

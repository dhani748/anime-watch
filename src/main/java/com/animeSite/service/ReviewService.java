package com.animeSite.service;

import com.animeSite.model.ReviewRequest;
import com.animeSite.persist.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ReviewService {
    Review addReview(UUID animeId, UUID userId, ReviewRequest request);
    Review updateReview(UUID reviewId, UUID userId, ReviewRequest request);
    Page<Review> getReviews(UUID animeId, Pageable pageable);
    void deleteReview(UUID reviewId);
    void deleteUserReview(UUID reviewId, UUID userId);
}

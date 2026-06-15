package com.animeSite.service.impl;

import com.animeSite.core.exception.BusinessException;
import com.animeSite.core.exception.ErrorCode;
import com.animeSite.core.exception.ValidationException;
import com.animeSite.model.ReviewRequest;
import com.animeSite.persist.Anime;
import com.animeSite.persist.Review;
import com.animeSite.persist.User;
import com.animeSite.repo.AnimeRepository;
import com.animeSite.repo.ReviewRepository;
import com.animeSite.repo.UserRepository;
import com.animeSite.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             AnimeRepository animeRepository,
                             UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.animeRepository = animeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Review addReview(UUID animeId, UUID userId, ReviewRequest request) {
        if (request.getStarRating() < 1 || request.getStarRating() > 5) {
            throw new ValidationException(ErrorCode.REVIEW_0003, "Star rating must be between 1 and 5");
        }
        if (reviewRepository.existsByAnimeIdAndUserId(animeId, userId)) {
            throw new BusinessException(ErrorCode.REVIEW_0002, "You have already reviewed this anime");
        }
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIME_0001, "Anime not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_0001, "User not found"));
        Review review = new Review();
        review.setAnime(anime);
        review.setUser(user);
        review.setStarRating(request.getStarRating());
        review.setComment(request.getComment());
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(UUID reviewId, UUID userId, ReviewRequest request) {
        if (request.getStarRating() < 1 || request.getStarRating() > 5) {
            throw new ValidationException(ErrorCode.REVIEW_0003, "Star rating must be between 1 and 5");
        }
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_0001, "Review not found"));
        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only edit your own reviews");
        }
        review.setStarRating(request.getStarRating());
        review.setComment(request.getComment());
        return reviewRepository.save(review);
    }

    public Page<Review> getReviews(UUID animeId, Pageable pageable) {
        return reviewRepository.findByAnimeId(animeId, pageable);
    }

    @Transactional
    public void deleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_0001, "Review not found"));
        reviewRepository.delete(review);
    }

    @Transactional
    public void deleteUserReview(UUID reviewId, UUID userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_0001, "Review not found"));
        if (!review.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only delete your own reviews");
        }
        reviewRepository.delete(review);
    }
}

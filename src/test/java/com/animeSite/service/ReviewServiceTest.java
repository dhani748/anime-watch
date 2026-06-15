package com.animeSite.service;
import com.animeSite.service.impl.ReviewServiceImpl;

import com.animeSite.model.ReviewRequest;
import com.animeSite.persist.Anime;
import com.animeSite.persist.Review;
import com.animeSite.persist.User;
import com.animeSite.repo.AnimeRepository;
import com.animeSite.repo.ReviewRepository;
import com.animeSite.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private AnimeRepository animeRepository;
    @Mock private UserRepository userRepository;

    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewServiceImpl(reviewRepository, animeRepository, userRepository);
    }

    @Test
    void addReview_ShouldSave_WhenValid() {
        ReviewRequest request = new ReviewRequest();
        request.setStarRating(5);
        request.setComment("Great!");

        UUID animeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Anime anime = new Anime();
        anime.setId(animeId);

        User user = new User();
        user.setId(userId);

        when(animeRepository.findById(animeId)).thenReturn(Optional.of(anime));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.existsByAnimeIdAndUserId(animeId, userId)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        Review result = reviewService.addReview(animeId, userId, request);

        assertEquals(5, result.getStarRating());
        assertEquals("Great!", result.getComment());
    }

    @Test
    void addReview_ShouldThrow_WhenDuplicate() {
        ReviewRequest request = new ReviewRequest();
        request.setStarRating(3);

        UUID animeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(reviewRepository.existsByAnimeIdAndUserId(animeId, userId)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> reviewService.addReview(animeId, userId, request));
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void addReview_ShouldThrow_WhenRatingOutOfRange() {
        ReviewRequest request = new ReviewRequest();
        request.setStarRating(6);

        assertThrows(RuntimeException.class, () -> reviewService.addReview(UUID.randomUUID(), UUID.randomUUID(), request));
    }

    @Test
    void updateReview_ShouldThrow_WhenNotOwner() {
        ReviewRequest request = new ReviewRequest();
        request.setStarRating(4);

        UUID reviewId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();

        User owner = new User();
        owner.setId(ownerId);

        Review review = new Review();
        review.setId(reviewId);
        review.setUser(owner);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        assertThrows(RuntimeException.class, () -> reviewService.updateReview(reviewId, otherId, request));
    }

    @Test
    void updateReview_ShouldSucceed_WhenOwner() {
        ReviewRequest request = new ReviewRequest();
        request.setStarRating(4);
        request.setComment("Updated");

        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        User owner = new User();
        owner.setId(userId);

        Review review = new Review();
        review.setId(reviewId);
        review.setUser(owner);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        Review result = reviewService.updateReview(reviewId, userId, request);

        assertEquals(4, result.getStarRating());
        assertEquals("Updated", result.getComment());
    }
}

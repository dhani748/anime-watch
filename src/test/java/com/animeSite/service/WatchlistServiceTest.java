package com.animeSite.service;
import com.animeSite.service.impl.WatchlistServiceImpl;

import com.animeSite.persist.Anime;
import com.animeSite.persist.User;
import com.animeSite.persist.Watchlist;
import com.animeSite.constant.WatchlistStatus;
import com.animeSite.repo.AnimeRepository;
import com.animeSite.repo.UserRepository;
import com.animeSite.repo.WatchlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock private WatchlistRepository watchlistRepository;
    @Mock private AnimeRepository animeRepository;
    @Mock private UserRepository userRepository;

    private WatchlistService watchlistService;

    @BeforeEach
    void setUp() {
        watchlistService = new WatchlistServiceImpl(watchlistRepository, animeRepository, userRepository);
    }

    @Test
    void addToWatchlist_ShouldSave_WhenNew() {
        UUID userId = UUID.randomUUID();
        UUID animeId = UUID.randomUUID();

        Anime anime = new Anime();
        anime.setId(animeId);

        User user = new User();
        user.setId(userId);

        when(animeRepository.findById(animeId)).thenReturn(Optional.of(anime));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(watchlistRepository.existsByUserIdAndAnimeId(userId, animeId)).thenReturn(false);
        when(watchlistRepository.save(any(Watchlist.class))).thenAnswer(i -> i.getArgument(0));

        Watchlist result = watchlistService.addToWatchlist(userId, animeId, WatchlistStatus.PLAN_TO_WATCH);
        assertEquals(WatchlistStatus.PLAN_TO_WATCH, result.getStatus());
    }

    @Test
    void addToWatchlist_ShouldThrow_WhenDuplicate() {
        UUID userId = UUID.randomUUID();
        UUID animeId = UUID.randomUUID();

        when(watchlistRepository.existsByUserIdAndAnimeId(userId, animeId)).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> watchlistService.addToWatchlist(userId, animeId, WatchlistStatus.PLAN_TO_WATCH));
        verify(watchlistRepository, never()).save(any());
    }

    @Test
    void updateStatus_ShouldThrow_WhenNotOwner() {
        UUID ownerId = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();
        UUID watchlistId = UUID.randomUUID();

        User owner = new User();
        owner.setId(ownerId);

        Watchlist entry = new Watchlist();
        entry.setId(watchlistId);
        entry.setUser(owner);

        when(watchlistRepository.findById(watchlistId)).thenReturn(Optional.of(entry));

        assertThrows(RuntimeException.class,
                () -> watchlistService.updateStatus(otherId, watchlistId, WatchlistStatus.WATCHING));
    }

    @Test
    void updateStatus_ShouldSucceed_WhenOwner() {
        UUID userId = UUID.randomUUID();
        UUID watchlistId = UUID.randomUUID();

        User owner = new User();
        owner.setId(userId);

        Watchlist entry = new Watchlist();
        entry.setId(watchlistId);
        entry.setUser(owner);
        entry.setStatus(WatchlistStatus.PLAN_TO_WATCH);

        when(watchlistRepository.findById(watchlistId)).thenReturn(Optional.of(entry));
        when(watchlistRepository.save(any(Watchlist.class))).thenAnswer(i -> i.getArgument(0));

        Watchlist result = watchlistService.updateStatus(userId, watchlistId, WatchlistStatus.WATCHING);
        assertEquals(WatchlistStatus.WATCHING, result.getStatus());
    }

    @Test
    void getMyWatchlist_ShouldReturnUserEntries() {
        UUID userId = UUID.randomUUID();
        when(watchlistRepository.findByUserId(userId)).thenReturn(List.of(new Watchlist()));
        assertEquals(1, watchlistService.getMyWatchlist(userId).size());
    }

    @Test
    void removeFromWatchlist_ShouldThrow_WhenNotOwner() {
        UUID ownerId = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();
        UUID watchlistId = UUID.randomUUID();

        User owner = new User();
        owner.setId(ownerId);

        Watchlist entry = new Watchlist();
        entry.setId(watchlistId);
        entry.setUser(owner);

        when(watchlistRepository.findById(watchlistId)).thenReturn(Optional.of(entry));

        assertThrows(RuntimeException.class,
                () -> watchlistService.removeFromWatchlist(otherId, watchlistId));
        verify(watchlistRepository, never()).delete(any());
    }
}

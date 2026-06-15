package com.animeSite.service.impl;

import com.animeSite.constant.WatchlistStatus;
import com.animeSite.core.exception.BusinessException;
import com.animeSite.core.exception.ErrorCode;
import com.animeSite.persist.Anime;
import com.animeSite.persist.User;
import com.animeSite.persist.Watchlist;
import com.animeSite.repo.AnimeRepository;
import com.animeSite.repo.UserRepository;
import com.animeSite.repo.WatchlistRepository;
import com.animeSite.service.WatchlistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    public WatchlistServiceImpl(WatchlistRepository watchlistRepository,
                                AnimeRepository animeRepository,
                                UserRepository userRepository) {
        this.watchlistRepository = watchlistRepository;
        this.animeRepository = animeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Watchlist addToWatchlist(UUID userId, UUID animeId, WatchlistStatus status) {
        if (watchlistRepository.existsByUserIdAndAnimeId(userId, animeId)) {
            throw new BusinessException(ErrorCode.ANIME_0002, "Anime already in your watchlist");
        }
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIME_0001, "Anime not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_0001, "User not found"));
        Watchlist entry = new Watchlist();
        entry.setUser(user);
        entry.setAnime(anime);
        entry.setStatus(status);
        return watchlistRepository.save(entry);
    }

    @Transactional
    public Watchlist updateStatus(UUID userId, UUID watchlistId, WatchlistStatus status) {
        Watchlist entry = watchlistRepository.findById(watchlistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GEN_0003, "Watchlist entry not found"));
        if (!entry.getUser().getId().equals(userId)) {
            throw new RuntimeException("Watchlist entry does not belong to the current user");
        }
        entry.setStatus(status);
        return watchlistRepository.save(entry);
    }

    public List<Watchlist> getMyWatchlist(UUID userId) {
        return watchlistRepository.findByUserId(userId);
    }

    @Transactional
    public void removeFromWatchlist(UUID userId, UUID watchlistId) {
        Watchlist entry = watchlistRepository.findById(watchlistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GEN_0003, "Watchlist entry not found"));
        if (!entry.getUser().getId().equals(userId)) {
            throw new RuntimeException("Watchlist entry does not belong to the current user");
        }
        watchlistRepository.delete(entry);
    }
}

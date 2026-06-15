package com.animeSite.service;

import com.animeSite.constant.WatchlistStatus;
import com.animeSite.persist.Watchlist;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WatchlistService {
    Watchlist addToWatchlist(UUID userId, UUID animeId, WatchlistStatus status);
    Watchlist updateStatus(UUID userId, UUID watchlistId, WatchlistStatus status);
    List<Watchlist> getMyWatchlist(UUID userId);
    void removeFromWatchlist(UUID userId, UUID watchlistId);
}

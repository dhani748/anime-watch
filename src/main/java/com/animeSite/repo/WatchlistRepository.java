package com.animeSite.repo;

import com.animeSite.persist.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, UUID> {
    List<Watchlist> findByUserId(UUID userId);
    Optional<Watchlist> findByUserIdAndAnimeId(UUID userId, UUID animeId);
    boolean existsByUserIdAndAnimeId(UUID userId, UUID animeId);
    void deleteByAnimeId(UUID animeId);
}

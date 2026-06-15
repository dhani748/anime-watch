package com.animeSite.repo;

import com.animeSite.persist.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, UUID> {
    List<Favorites> findByUserId(UUID userId);
    Optional<Favorites> findByUserIdAndAnimeId(UUID userId, UUID animeId);
    boolean existsByUserIdAndAnimeId(UUID userId, UUID animeId);
    void deleteByAnimeId(UUID animeId);
}

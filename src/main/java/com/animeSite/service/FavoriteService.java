package com.animeSite.service;

import com.animeSite.persist.Favorites;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {
    Favorites addToFavorites(UUID userId, UUID animeId);
    List<Favorites> getMyFavorites(UUID userId);
    void removeFromFavorites(UUID userId, UUID animeId);
}

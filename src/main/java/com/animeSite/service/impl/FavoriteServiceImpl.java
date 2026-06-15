package com.animeSite.service.impl;

import com.animeSite.core.exception.BusinessException;
import com.animeSite.core.exception.ErrorCode;
import com.animeSite.persist.Anime;
import com.animeSite.persist.Favorites;
import com.animeSite.persist.User;
import com.animeSite.repo.AnimeRepository;
import com.animeSite.repo.FavoritesRepository;
import com.animeSite.repo.UserRepository;
import com.animeSite.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoritesRepository favoritesRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    public FavoriteServiceImpl(FavoritesRepository favoritesRepository,
                               AnimeRepository animeRepository,
                               UserRepository userRepository) {
        this.favoritesRepository = favoritesRepository;
        this.animeRepository = animeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Favorites addToFavorites(UUID userId, UUID animeId) {
        if (favoritesRepository.existsByUserIdAndAnimeId(userId, animeId)) {
            throw new BusinessException(ErrorCode.ANIME_0002, "Anime already in your favorites");
        }
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ANIME_0001, "Anime not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_0001, "User not found"));
        Favorites fav = new Favorites();
        fav.setUser(user);
        fav.setAnime(anime);
        return favoritesRepository.save(fav);
    }

    public List<Favorites> getMyFavorites(UUID userId) {
        return favoritesRepository.findByUserId(userId);
    }

    @Transactional
    public void removeFromFavorites(UUID userId, UUID animeId) {
        Favorites fav = favoritesRepository.findByUserIdAndAnimeId(userId, animeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GEN_0003, "Favorite not found"));
        favoritesRepository.delete(fav);
    }
}

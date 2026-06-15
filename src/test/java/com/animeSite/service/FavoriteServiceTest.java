package com.animeSite.service;
import com.animeSite.service.impl.FavoriteServiceImpl;

import com.animeSite.persist.Anime;
import com.animeSite.persist.Favorites;
import com.animeSite.persist.User;
import com.animeSite.repo.AnimeRepository;
import com.animeSite.repo.FavoritesRepository;
import com.animeSite.repo.UserRepository;
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
class FavoriteServiceTest {

    @Mock private FavoritesRepository favoritesRepository;
    @Mock private AnimeRepository animeRepository;
    @Mock private UserRepository userRepository;

    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        favoriteService = new FavoriteServiceImpl(favoritesRepository, animeRepository, userRepository);
    }

    @Test
    void addToFavorites_ShouldSave_WhenNew() {
        UUID userId = UUID.randomUUID();
        UUID animeId = UUID.randomUUID();

        Anime anime = new Anime();
        anime.setId(animeId);

        User user = new User();
        user.setId(userId);

        when(favoritesRepository.existsByUserIdAndAnimeId(userId, animeId)).thenReturn(false);
        when(animeRepository.findById(animeId)).thenReturn(Optional.of(anime));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(favoritesRepository.save(any(Favorites.class))).thenAnswer(i -> i.getArgument(0));

        Favorites result = favoriteService.addToFavorites(userId, animeId);
        assertNotNull(result);
    }

    @Test
    void addToFavorites_ShouldThrow_WhenDuplicate() {
        when(favoritesRepository.existsByUserIdAndAnimeId(any(UUID.class), any(UUID.class))).thenReturn(true);
        assertThrows(RuntimeException.class,
                () -> favoriteService.addToFavorites(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void getMyFavorites_ShouldReturnList() {
        UUID userId = UUID.randomUUID();
        when(favoritesRepository.findByUserId(userId)).thenReturn(List.of(new Favorites()));
        assertEquals(1, favoriteService.getMyFavorites(userId).size());
    }

    @Test
    void removeFromFavorites_ShouldDelete_WhenExists() {
        UUID userId = UUID.randomUUID();
        UUID animeId = UUID.randomUUID();

        Favorites fav = new Favorites();
        when(favoritesRepository.findByUserIdAndAnimeId(userId, animeId)).thenReturn(Optional.of(fav));

        favoriteService.removeFromFavorites(userId, animeId);
        verify(favoritesRepository).delete(fav);
    }

    @Test
    void removeFromFavorites_ShouldThrow_WhenNotFound() {
        when(favoritesRepository.findByUserIdAndAnimeId(any(UUID.class), any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> favoriteService.removeFromFavorites(UUID.randomUUID(), UUID.randomUUID()));
    }
}

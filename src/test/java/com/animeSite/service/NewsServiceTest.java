package com.animeSite.service;
import com.animeSite.service.impl.NewsServiceImpl;

import com.animeSite.model.NewsRequest;
import com.animeSite.persist.NewsArticle;
import com.animeSite.repo.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock private NewsRepository newsRepository;

    private NewsService newsService;

    @BeforeEach
    void setUp() {
        newsService = new NewsServiceImpl(newsRepository);
    }

    @Test
    void getAllNews_ShouldReturnPagedResults() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(newsRepository.findAllByOrderByCreatedAtDesc(pageable))
                .thenReturn(new PageImpl<>(List.of(new NewsArticle())));

        Page<NewsArticle> result = newsService.getAllNews(pageable);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getNewsById_ShouldReturnArticle() {
        UUID id = UUID.randomUUID();
        NewsArticle article = new NewsArticle();
        article.setId(id);
        when(newsRepository.findById(id)).thenReturn(Optional.of(article));

        NewsArticle result = newsService.getNewsById(id);
        assertEquals(id, result.getId());
    }

    @Test
    void getNewsById_ShouldThrow_WhenNotFound() {
        when(newsRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> newsService.getNewsById(UUID.randomUUID()));
    }

    @Test
    void createNews_ShouldSetAuthorAndSave() {
        NewsRequest request = new NewsRequest();
        request.setTitle("Title");
        request.setContent("Content");

        when(newsRepository.save(any(NewsArticle.class))).thenAnswer(i -> i.getArgument(0));

        NewsArticle result = newsService.createNews(request, "admin@test.com");
        assertEquals("Title", result.getTitle());
        assertEquals("admin@test.com", result.getAuthor());
    }

    @Test
    void deleteNews_ShouldCallRepository() {
        newsService.deleteNews(UUID.randomUUID());
        verify(newsRepository).deleteById(any(UUID.class));
    }
}

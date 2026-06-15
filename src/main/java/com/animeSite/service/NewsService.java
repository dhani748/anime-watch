package com.animeSite.service;

import com.animeSite.model.NewsRequest;
import com.animeSite.persist.NewsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface NewsService {
    Page<NewsArticle> getAllNews(Pageable pageable);
    NewsArticle getNewsById(UUID id);
    NewsArticle createNews(NewsRequest request, String author);
    NewsArticle updateNews(UUID id, NewsRequest request);
    void deleteNews(UUID id);
}

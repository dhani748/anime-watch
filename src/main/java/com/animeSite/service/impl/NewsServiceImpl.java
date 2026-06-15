package com.animeSite.service.impl;

import com.animeSite.core.exception.BusinessException;
import com.animeSite.core.exception.ErrorCode;
import com.animeSite.model.NewsRequest;
import com.animeSite.persist.NewsArticle;
import com.animeSite.repo.NewsRepository;
import com.animeSite.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Page<NewsArticle> getAllNews(Pageable pageable) {
        return newsRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public NewsArticle getNewsById(UUID id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_0001, "News article not found"));
    }

    @Transactional
    public NewsArticle createNews(NewsRequest request, String author) {
        NewsArticle article = new NewsArticle();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setImageUrl(request.getImageUrl());
        article.setAuthor(author);
        return newsRepository.save(article);
    }

    @Transactional
    public NewsArticle updateNews(UUID id, NewsRequest request) {
        NewsArticle article = newsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NEWS_0001, "News article not found"));
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setImageUrl(request.getImageUrl());
        return newsRepository.save(article);
    }

    @Transactional
    public void deleteNews(UUID id) {
        newsRepository.deleteById(id);
    }
}

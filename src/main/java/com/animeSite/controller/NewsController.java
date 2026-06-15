package com.animeSite.controller;

import com.animeSite.core.model.ApiResponse;
import com.animeSite.model.NewsRequest;
import com.animeSite.persist.NewsArticle;
import java.util.UUID;
import com.animeSite.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@Tag(name = "News", description = "News articles endpoints")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    @Operation(summary = "Get all news", description = "Returns paginated news articles.")
    public ResponseEntity<ApiResponse<Page<NewsArticle>>> getAllNews(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        Page<NewsArticle> articles = newsService.getAllNews(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.success(articles, articles.getNumber(), articles.getTotalPages()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get news by ID", description = "Returns a single news article.")
    public ResponseEntity<ApiResponse<NewsArticle>> getNewsById(
            @Parameter(description = "News ID", required = true) @PathVariable UUID id) {
        NewsArticle article = newsService.getNewsById(id);
        return ResponseEntity.ok(ApiResponse.success(article));
    }

    @PostMapping
    @Operation(summary = "Create news", description = "Creates a news article (Admin only).")
    public ResponseEntity<ApiResponse<NewsArticle>> createNews(@Valid @RequestBody NewsRequest request,
                                                                Authentication authentication) {
        String author = authentication.getName();
        NewsArticle article = newsService.createNews(request, author);
        return ResponseEntity.ok(ApiResponse.success(article));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update news", description = "Updates a news article (Admin only).")
    public ResponseEntity<ApiResponse<NewsArticle>> updateNews(
            @Parameter(description = "News ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody NewsRequest request) {
        NewsArticle article = newsService.updateNews(id, request);
        return ResponseEntity.ok(ApiResponse.success(article));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete news", description = "Deletes a news article (Admin only).")
    public ResponseEntity<ApiResponse<String>> deleteNews(
            @Parameter(description = "News ID", required = true) @PathVariable UUID id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok(ApiResponse.success("News article deleted successfully"));
    }
}

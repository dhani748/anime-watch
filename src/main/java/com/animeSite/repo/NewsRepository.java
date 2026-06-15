package com.animeSite.repo;

import com.animeSite.persist.NewsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<NewsArticle, UUID> {
    Page<NewsArticle> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

package com.animeSite.persist;

import com.animeSite.core.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "news_articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticle extends Auditable {

    @Id
    @Column(length = 36)
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String author;
}

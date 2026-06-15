package com.animeSite.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> {
    private List<T> content;
    private Integer page;
    private Integer size;
    private Long totalElements;

    public static <T> PageResponse<T> of(List<T> content) {
        return new PageResponse<>(content, 0, content.size(), (long) content.size());
    }

    public static <T> PageResponse<T> empty() {
        return new PageResponse<>(Collections.emptyList(), 0, 0, 0L);
    }
}

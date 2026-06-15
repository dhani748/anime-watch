package com.animeSite.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JikanPagination {

    @JsonProperty("has_next_page")
    private boolean hasNextPage;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("last_visible_page")
    private int lastVisiblePage;
}

package com.animeSite.model;

import lombok.Data;
import java.util.List;

@Data
public class JikanListResponse {

    private List<JikanAnimeData> data;
    private JikanPagination pagination;
}

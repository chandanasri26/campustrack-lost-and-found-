package com.campus.lostandfound.dto;

import java.util.List;

public record ItemStatsDto(
        long total,
        long lost,
        long found,
        long resolved,
        long open,
        List<CategoryCountDto> byCategory
) {
    public record CategoryCountDto(String category, long count) {}
}

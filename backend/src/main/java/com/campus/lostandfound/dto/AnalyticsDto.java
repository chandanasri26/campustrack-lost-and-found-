package com.campus.lostandfound.dto;

import java.util.List;

public record AnalyticsDto(
        long totalItems,
        long matchedItems,
        long recoveredItems,
        long activeUsers,
        List<ItemStatsDto.CategoryCountDto> byCategory,
        List<LocationCountDto> byLocation
) {
    public record LocationCountDto(String location, long count) {}
}

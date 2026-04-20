package com.campus.lostandfound.dto;

public record AdminStatsDto(
        long totalUsers,
        long totalItems,
        long lostItems,
        long foundItems,
        long resolvedItems,
        long openItems,
        long blockedUsers,
        long recentActivity
) {}

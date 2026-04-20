package com.campus.lostandfound.dto;

import com.campus.lostandfound.model.Item;
import java.time.LocalDateTime;
import java.util.List;

public record ItemDto(
        String id,
        String title,
        String description,
        String category,
        String type,
        String status,
        boolean approved,
        String location,
        List<String> tags,
        String imageUrl,
        String userId,
        String userName,
        String userEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ItemDto from(Item item) {
        return new ItemDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getCategory(),
                item.getType(),
                item.getStatus(),
                item.isApproved(),
                item.getLocation(),
                item.getTags(),
                item.getImageUrl(),
                item.getUserId(),
                item.getUserName(),
                item.getUserEmail(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}

package com.campus.lostandfound.dto;

import java.time.LocalDateTime;

public record MessageConversationDto(
        String itemId,
        String itemTitle,
        String itemType,
        String itemCategory,
        String otherUserId,
        String otherUserName,
        String lastMessage,
        int unreadCount,
        LocalDateTime updatedAt
) {
}

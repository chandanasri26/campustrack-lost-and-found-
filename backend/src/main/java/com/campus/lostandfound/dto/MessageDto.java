package com.campus.lostandfound.dto;

import com.campus.lostandfound.model.Message;

import java.time.LocalDateTime;

public record MessageDto(
        String id,
        String itemId,
        String senderId,
        String receiverId,
        String content,
        boolean read,
        LocalDateTime createdAt
) {
    public static MessageDto from(Message message) {
        return new MessageDto(
                message.getId(),
                message.getItemId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent(),
                message.isRead(),
                message.getCreatedAt()
        );
    }
}

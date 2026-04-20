package com.campus.lostandfound.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateMessageRequest {
    @NotBlank
    private String itemId;

    @NotBlank
    private String receiverId;

    @NotBlank
    private String content;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

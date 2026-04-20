package com.campus.lostandfound.controller;

import com.campus.lostandfound.dto.CreateMessageRequest;
import com.campus.lostandfound.dto.MessageConversationDto;
import com.campus.lostandfound.dto.MessageDto;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(
            @Valid @RequestBody CreateMessageRequest request,
            @AuthenticationPrincipal User sender) {
        return ResponseEntity.ok(messageService.sendMessage(request, sender));
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<MessageDto>> getInbox(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(messageService.getInbox(currentUser));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<MessageDto>> getSent(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(messageService.getSent(currentUser));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<MessageDto>> getMessagesForItem(
            @PathVariable String itemId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(messageService.getMessagesForItem(itemId, currentUser));
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<MessageConversationDto>> getConversations(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(messageService.getConversations(currentUser));
    }

    @PatchMapping("/item/{itemId}/mark-read")
    public ResponseEntity<Void> markRead(
            @PathVariable String itemId,
            @AuthenticationPrincipal User currentUser) {
        messageService.markMessagesRead(itemId, currentUser);
        return ResponseEntity.noContent().build();
    }
}
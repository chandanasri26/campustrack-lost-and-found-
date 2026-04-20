package com.campus.lostandfound.controller;

import com.campus.lostandfound.dto.*;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String approved) {
        return ResponseEntity.ok(itemService.getItems(search, category, type, status, approved));
    }

    @GetMapping("/stats")
    public ResponseEntity<ItemStatsDto> getStats() {
        return ResponseEntity.ok(itemService.getStats());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ItemDto>> getRecentItems(@RequestParam(required = false) Integer limit) {
        return ResponseEntity.ok(itemService.getRecentItems(limit));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ItemDto>> getMyItems(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(itemService.getMyItems(currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable String id) {
        try {
            return ResponseEntity.ok(itemService.getItem(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createItem(
            @Valid @RequestBody CreateItemRequest request,
            @AuthenticationPrincipal User currentUser) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(request, currentUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateItem(
            @PathVariable String id,
            @RequestBody UpdateItemRequest request,
            @AuthenticationPrincipal User currentUser) {
        try {
            return ResponseEntity.ok(itemService.updateItem(id, request, currentUser));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable String id, @AuthenticationPrincipal User currentUser) {
        try {
            itemService.deleteItem(id, currentUser);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<?> resolveItem(@PathVariable String id, @AuthenticationPrincipal User currentUser) {
        try {
            return ResponseEntity.ok(itemService.resolveItem(id, currentUser));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal User currentUser) {
        try {
            String imageUrl = itemService.uploadImage(
                    request.get("imageData"),
                    request.getOrDefault("fileName", "upload.jpg"));
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Image upload failed: " + e.getMessage()));
        }
    }
}

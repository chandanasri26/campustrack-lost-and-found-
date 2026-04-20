package com.campus.lostandfound.controller;

import com.campus.lostandfound.dto.AdminStatsDto;
import com.campus.lostandfound.dto.ItemDto;
import com.campus.lostandfound.dto.UserDto;
import com.campus.lostandfound.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PatchMapping("/users/{id}/block")
    public ResponseEntity<?> blockUser(@PathVariable String id) {
        try {
            return ResponseEntity.ok(adminService.blockUser(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/users/{id}/unblock")
    public ResponseEntity<?> unblockUser(@PathVariable String id) {
        try {
            return ResponseEntity.ok(adminService.unblockUser(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemDto>> getItems() {
        return ResponseEntity.ok(adminService.getAllItems());
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable String id) {
        try {
            adminService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/items/{id}/approve")
    public ResponseEntity<?> approveItem(@PathVariable String id) {
        try {
            return ResponseEntity.ok(adminService.approveItem(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStatsDto> getStats() {
        return ResponseEntity.ok(adminService.getStats());
    }
}

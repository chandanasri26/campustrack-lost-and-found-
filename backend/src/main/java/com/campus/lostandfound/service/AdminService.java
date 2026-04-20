package com.campus.lostandfound.service;

import com.campus.lostandfound.dto.AdminStatsDto;
import com.campus.lostandfound.dto.ItemDto;
import com.campus.lostandfound.dto.UserDto;
import com.campus.lostandfound.model.Item;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.repository.ItemRepository;
import com.campus.lostandfound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public UserDto blockUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBlocked(true);
        return UserDto.from(userRepository.save(user));
    }

    public UserDto unblockUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBlocked(false);
        return UserDto.from(userRepository.save(user));
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .map(ItemDto::from)
                .collect(Collectors.toList());
    }

    public void deleteItem(String id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        itemRepository.delete(item);
    }

    public ItemDto approveItem(String id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        item.setApproved(true);
        item.setUpdatedAt(LocalDateTime.now());
        return ItemDto.from(itemRepository.save(item));
    }

    public AdminStatsDto getStats() {
        long totalUsers = userRepository.count();
        long totalItems = itemRepository.count();
        long lostItems = itemRepository.countByType("lost");
        long foundItems = itemRepository.countByType("found");
        long resolvedItems = itemRepository.countByStatus("resolved");
        long openItems = itemRepository.countByStatus("open");
        long blockedUsers = userRepository.countByBlockedTrue();
        long recentActivity = itemRepository.countByCreatedAtAfter(LocalDateTime.now().minusDays(7));

        return new AdminStatsDto(totalUsers, totalItems, lostItems, foundItems,
                resolvedItems, openItems, blockedUsers, recentActivity);
    }
}

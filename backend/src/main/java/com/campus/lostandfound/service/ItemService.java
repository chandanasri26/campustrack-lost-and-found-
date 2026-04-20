package com.campus.lostandfound.service;

import com.campus.lostandfound.dto.CreateItemRequest;
import com.campus.lostandfound.dto.ItemDto;
import com.campus.lostandfound.dto.ItemStatsDto;
import com.campus.lostandfound.dto.UpdateItemRequest;
import com.campus.lostandfound.model.Item;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.model.UserRole;
import com.campus.lostandfound.repository.ItemRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MongoTemplate mongoTemplate;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    public List<ItemDto> getItems(String search, String category, String type, String status, String approved) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();

        if (search != null && !search.isBlank()) {
            criteriaList.add(new Criteria().orOperator(
                    Criteria.where("title").regex(search, "i"),
                    Criteria.where("description").regex(search, "i")));
        }
        if (category != null && !category.isBlank()) {
            criteriaList.add(Criteria.where("category").is(category));
        }
        if (type != null && !type.isBlank()) {
            criteriaList.add(Criteria.where("type").is(type));
        }
        if (status != null && !status.isBlank()) {
            criteriaList.add(Criteria.where("status").is(status));
        }
        if (approved == null || approved.isBlank()) {
            criteriaList.add(Criteria.where("approved").is(true));
        } else {
            criteriaList.add(Criteria.where("approved").is(Boolean.parseBoolean(approved)));
        }

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

        return mongoTemplate.find(query, Item.class).stream()
                .map(ItemDto::from)
                .collect(Collectors.toList());
    }

    public ItemDto getItem(String id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        return ItemDto.from(item);
    }

    public ItemDto createItem(CreateItemRequest request, User currentUser) {
        Item item = new Item();
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setCategory(request.getCategory());
        item.setType(request.getType());
        item.setLocation(request.getLocation());
        item.setImageUrl(request.getImageUrl());
        item.setApproved(false);
        item.setUserId(currentUser.getId());
        item.setUserName(currentUser.getName());
        item.setUserEmail(currentUser.getEmail());
        item.setStatus("open");
        return ItemDto.from(itemRepository.save(item));
    }

    public ItemDto updateItem(String id, UpdateItemRequest request, User currentUser) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        boolean isOwner = item.getUserId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new SecurityException("Access denied");
        }

        if (request.getTitle() != null)
            item.setTitle(request.getTitle());
        if (request.getDescription() != null)
            item.setDescription(request.getDescription());
        if (request.getCategory() != null)
            item.setCategory(request.getCategory());
        if (request.getLocation() != null)
            item.setLocation(request.getLocation());
        if (request.getImageUrl() != null)
            item.setImageUrl(request.getImageUrl());
        if (request.getStatus() != null)
            item.setStatus(request.getStatus());
        if (request.getApproved() != null)
            item.setApproved(request.getApproved());
        item.setUpdatedAt(LocalDateTime.now());

        return ItemDto.from(itemRepository.save(item));
    }

    public void deleteItem(String id, User currentUser) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        boolean isOwner = item.getUserId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new SecurityException("Access denied");
        }

        itemRepository.delete(item);
    }

    public ItemDto resolveItem(String id, User currentUser) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        boolean isOwner = item.getUserId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == UserRole.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new SecurityException("Access denied");
        }

        item.setStatus("resolved");
        item.setUpdatedAt(LocalDateTime.now());
        return ItemDto.from(itemRepository.save(item));
    }

    public List<ItemDto> getMyItems(User currentUser) {
        return itemRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId())
                .stream().map(ItemDto::from).collect(Collectors.toList());
    }

    public List<ItemDto> getRecentItems(Integer limit) {
        int count = (limit != null) ? limit : 10;
        return itemRepository.findTop10ByOrderByCreatedAtDesc()
                .stream().limit(count).map(ItemDto::from).collect(Collectors.toList());
    }

    public ItemStatsDto getStats() {
        long total = itemRepository.count();
        long lost = itemRepository.countByType("lost");
        long found = itemRepository.countByType("found");
        long resolved = itemRepository.countByStatus("resolved");
        long open = itemRepository.countByStatus("open");

        GroupOperation groupByCategory = Aggregation.group("category").count().as("count");
        ProjectionOperation project = Aggregation.project("count").and("_id").as("category");
        Aggregation aggregation = Aggregation.newAggregation(groupByCategory, project);
        AggregationResults<CategoryCountResult> results = mongoTemplate.aggregate(aggregation, "items",
                CategoryCountResult.class);

        List<ItemStatsDto.CategoryCountDto> byCategory = results.getMappedResults().stream()
                .map(r -> new ItemStatsDto.CategoryCountDto(r.getCategory(), r.getCount()))
                .collect(Collectors.toList());

        return new ItemStatsDto(total, lost, found, resolved, open, byCategory);
    }

    public String uploadImage(String imageData, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String base64Data = imageData.contains(",") ? imageData.split(",")[1] : imageData;
        byte[] decoded = Base64.getDecoder().decode(base64Data);
        String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : ".jpg";
        String uniqueFileName = UUID.randomUUID() + extension;
        Files.write(uploadPath.resolve(uniqueFileName), decoded);
        return "/api/uploads/" + uniqueFileName;
    }

    @Data
    private static class CategoryCountResult {
        private String category;
        private long count;
    }
}

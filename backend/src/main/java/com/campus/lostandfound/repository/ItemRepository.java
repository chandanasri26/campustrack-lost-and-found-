package com.campus.lostandfound.repository;

import com.campus.lostandfound.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByUserIdOrderByCreatedAtDesc(String userId);

    List<Item> findTop10ByOrderByCreatedAtDesc();

    long countByType(String type);

    long countByStatus(String status);

    long countByCreatedAtAfter(LocalDateTime date);
}

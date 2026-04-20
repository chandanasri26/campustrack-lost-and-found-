package com.campus.lostandfound.repository;

import com.campus.lostandfound.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByReceiverIdOrderByCreatedAtDesc(String receiverId);
    List<Message> findBySenderIdOrderByCreatedAtDesc(String senderId);
    List<Message> findBySenderIdOrReceiverIdOrderByCreatedAtDesc(String senderId, String receiverId);
    List<Message> findByItemIdAndReceiverIdAndReadFalse(String itemId, String receiverId);

    @Query("{ 'itemId': ?0, $or: [ { 'senderId': ?1 }, { 'receiverId': ?1 } ] }")
    List<Message> findByItemIdAndParticipantsOrderByCreatedAtAsc(String itemId, String participantId);
}

package com.campus.lostandfound.service;

import com.campus.lostandfound.dto.CreateMessageRequest;
import com.campus.lostandfound.dto.MessageConversationDto;
import com.campus.lostandfound.dto.MessageDto;
import com.campus.lostandfound.dto.ItemDto;
import com.campus.lostandfound.model.Message;
import com.campus.lostandfound.model.User;
import com.campus.lostandfound.repository.MessageRepository;
import com.campus.lostandfound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    public MessageDto sendMessage(CreateMessageRequest request, User sender) {
        userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        Message message = new Message();
        message.setItemId(request.getItemId());
        message.setSenderId(sender.getId());
        message.setReceiverId(request.getReceiverId());
        message.setContent(request.getContent());
        return MessageDto.from(messageRepository.save(message));
    }

    public List<MessageDto> getInbox(User currentUser) {
        return messageRepository.findByReceiverIdOrderByCreatedAtDesc(currentUser.getId())
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public List<MessageDto> getSent(User currentUser) {
        return messageRepository.findBySenderIdOrderByCreatedAtDesc(currentUser.getId())
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public List<MessageDto> getMessagesForItem(String itemId, User currentUser) {
        return messageRepository.findByItemIdAndParticipantsOrderByCreatedAtAsc(itemId, currentUser.getId())
                .stream().map(MessageDto::from).collect(Collectors.toList());
    }

    public List<MessageConversationDto> getConversations(User currentUser) {
        List<Message> messages = messageRepository.findBySenderIdOrReceiverIdOrderByCreatedAtDesc(currentUser.getId(), currentUser.getId());
        Map<String, ConversationAccumulator> threads = new LinkedHashMap<>();

        for (Message message : messages) {
            String otherUserId = message.getSenderId().equals(currentUser.getId())
                    ? message.getReceiverId()
                    : message.getSenderId();
            String threadKey = message.getItemId() + "|" + otherUserId;
            ConversationAccumulator accumulator = threads.computeIfAbsent(threadKey, key -> {
                User otherUser = userRepository.findById(otherUserId).orElse(null);
                ItemDto item = null;
                try {
                    item = itemService.getItem(message.getItemId());
                } catch (IllegalArgumentException ignored) {
                }
                return new ConversationAccumulator(
                        message.getItemId(),
                        item != null ? item.title() : "Item",
                        item != null ? item.type() : "lost",
                        item != null ? item.category() : "Unknown",
                        otherUserId,
                        otherUser != null ? otherUser.getName() : "Unknown"
                );
            });
            accumulator.addMessage(message, currentUser.getId());
        }

        return threads.values().stream().map(ConversationAccumulator::toDto).collect(Collectors.toList());
    }

    public void markMessagesRead(String itemId, User currentUser) {
        List<Message> unreadMessages = messageRepository.findByItemIdAndReceiverIdAndReadFalse(itemId, currentUser.getId());
        if (!unreadMessages.isEmpty()) {
            unreadMessages.forEach(message -> message.setRead(true));
            messageRepository.saveAll(unreadMessages);
        }
    }

    private static class ConversationAccumulator {
        private final String itemId;
        private final String itemTitle;
        private final String itemType;
        private final String itemCategory;
        private final String otherUserId;
        private final String otherUserName;
        private String lastMessage;
        private int unreadCount;
        private java.time.LocalDateTime updatedAt;

        public ConversationAccumulator(String itemId, String itemTitle, String itemType, String itemCategory, String otherUserId, String otherUserName) {
            this.itemId = itemId;
            this.itemTitle = itemTitle;
            this.itemType = itemType;
            this.itemCategory = itemCategory;
            this.otherUserId = otherUserId;
            this.otherUserName = otherUserName;
        }

        public void addMessage(Message message, String currentUserId) {
            if (lastMessage == null) {
                lastMessage = message.getContent();
                updatedAt = message.getCreatedAt();
            }
            if (!message.isRead() && message.getReceiverId().equals(currentUserId)) {
                unreadCount++;
            }
        }

        public MessageConversationDto toDto() {
            return new MessageConversationDto(
                    itemId,
                    itemTitle,
                    itemType,
                    itemCategory,
                    otherUserId,
                    otherUserName,
                    lastMessage != null ? lastMessage : "",
                    unreadCount,
                    updatedAt != null ? updatedAt : java.time.LocalDateTime.now()
            );
        }
    }
}

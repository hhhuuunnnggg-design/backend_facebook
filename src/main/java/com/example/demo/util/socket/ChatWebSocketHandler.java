package com.example.demo.util.socket;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.domain.response.MessageDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public ChatWebSocketHandler(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = Long.valueOf(session.getUri().getQuery().split("=")[1]);
        sessions.put(userId, session);
        System.out.println("Connected: " + userId + " - Session: " + session.getId());
        session.sendMessage(new TextMessage("Welcome! You are connected."));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Lấy senderId từ URL thay vì JSON
        Long senderId = Long.valueOf(session.getUri().getQuery().split("=")[1]);
        // Parse JSON chỉ cần receiverId và content
        Map<String, String> data = objectMapper.readValue(message.getPayload(), Map.class);
        Long receiverId = Long.valueOf(data.get("receiverId"));
        String content = data.get("content");

        User sender = userRepository.findById(senderId).orElse(null);
        User receiver = userRepository.findById(receiverId).orElse(null);
        if (sender != null && receiver != null) {
            Message msg = messageService.saveMessage(sender, receiver, content);
            WebSocketSession receiverSession = sessions.get(receiverId);
            if (receiverSession != null && receiverSession.isOpen()) {
                String firstname = Objects.toString(sender.getFirstname(), "");
                String latename = Objects.toString(sender.getLatename(), "");
                String fullname = (firstname + " " + latename).trim();
                MessageDTO messageDTO = new MessageDTO(msg.getContent(), fullname, sender.getEmail());
                receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDTO)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = Long.valueOf(session.getUri().getQuery().split("=")[1]);
        sessions.remove(userId);
        System.out.println("Disconnected: " + userId);
    }
}
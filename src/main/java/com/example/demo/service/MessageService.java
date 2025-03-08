package com.example.demo.service;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(User sender, User receiver, String content) {
        Message message = new Message();
        message.setUser1(sender);
        message.setUser2(receiver);
        message.setContent(content);
        return messageRepository.save(message);
    }
}

package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.model.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> sessionAttribs = headerAccessor.getSessionAttributes();
        String sender = chatMessage.getSender();
        String receiver = chatMessage.getReceiver();
        sessionAttribs.put("username", sender);
        sessionAttribs.put("topicname", receiver);
        Topics.updateSizes(receiver, true);
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getReceiver(), chatMessage);
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getReceiver(), chatMessage);
    }

    @GetMapping("/topic-sizes")
    public Map<String, Integer> getTopicSizes() {
        return Topics.getSizes();
    }
}

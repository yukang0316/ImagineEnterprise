package hello.imagine.community.controller;

import hello.imagine.community.dto.ChatMessageDTO;
import hello.imagine.community.model.ChatMessage;
import hello.imagine.community.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/message")
    public ChatMessage saveMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        return chatService.saveMessage(chatMessageDTO);
    }

    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatService.getAllChatMessages();
    }
}
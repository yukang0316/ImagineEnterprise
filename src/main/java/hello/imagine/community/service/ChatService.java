package hello.imagine.community.service;

import hello.imagine.community.dto.ChatMessageDTO;
import hello.imagine.community.model.ChatMessage;
import hello.imagine.community.repository.ChatMessageRepository;
import hello.imagine.login.model.Member;
import hello.imagine.login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private MemberService memberService;

    public ChatMessage createChatMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(chatMessageDTO.getContent());

        // sender를 Member 객체로 변환하여 설정
        Member sender = memberService.findById(chatMessageDTO.getSenderId());
        chatMessage.setSender(sender);

        chatMessage.setTimestamp(chatMessageDTO.getTimestamp());
        return chatMessageRepository.save(chatMessage);
    }

    public ChatMessage saveMessage(ChatMessageDTO chatMessageDTO) {
        return createChatMessage(chatMessageDTO);
    }

    public List<ChatMessage> getAllChatMessages() {
        return chatMessageRepository.findAll();
    }
}
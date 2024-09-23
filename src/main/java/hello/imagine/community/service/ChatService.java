package hello.imagine.community.service;

import hello.imagine.community.dto.ChatMessageDTO;
import hello.imagine.community.dto.ChatRoomDTO;
import hello.imagine.community.model.ChatMessage;
import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.repository.ChatMessageRepository;
import hello.imagine.community.repository.ChatRoomRepository;
import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import hello.imagine.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ChatRoom createChatRoom(ChatRoomDTO chatRoomDTO, String userId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(chatRoomDTO.getName());
        chatRoom.setDescription(chatRoomDTO.getDescription());
        chatRoom.setMaxParticipants(chatRoomDTO.getMaxParticipants());

        // JWT에서 userId를 가져와서 사용합니다.
        Member creator = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        chatRoom.getParticipants().add(creator);
        chatRoom.setCategory(chatRoomDTO.getCategory());

        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom joinChatRoom(Long roomId, Long memberId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        if (chatRoom.getParticipants().size() < chatRoom.getMaxParticipants()) {
            chatRoom.getParticipants().add(member);
            chatRoomRepository.save(chatRoom);
        } else {
            throw new IllegalStateException("Chat room is full");
        }

        return chatRoom;
    }

    public ChatMessage sendMessage(Long roomId, ChatMessageDTO chatMessageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        Member sender = memberRepository.findById(chatMessageDTO.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setSender(sender);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setTimestamp(LocalDateTime.now());

        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatRoom> getChatRoomsByCategory(String category) {
        return chatRoomRepository.findByCategoryOrderByParticipantsDesc(category);
    }

    public Long getMemberIdByUserId(String userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return member.getMemberId();
    }

    // 참여자가 5명 이상인 채팅방을 가져오는 메서드
    public List<ChatRoom> getPopularChatRooms() {
        return chatRoomRepository.findByParticipantCountGreaterThanEqual(5);
    }
}

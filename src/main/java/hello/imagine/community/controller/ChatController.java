package hello.imagine.community.controller;

import hello.imagine.community.dto.ChatMessageDTO;
import hello.imagine.community.dto.ChatRoomDTO;
import hello.imagine.community.model.ChatMessage;
import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.service.ChatService;
import hello.imagine.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO, @RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token.substring(7));  // "Bearer " 제거 후, extractUserId 사용
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(chatService.createChatRoom(chatRoomDTO, userId));
    }

    //실시간 채팅방 눌렀을때 리스트 반환
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoom>> getChatRoomsByCategory(@RequestParam Long categoryId) {
        return ResponseEntity.ok(chatService.getChatRoomsByCategory(categoryId));
    }

    @PostMapping("/room/{roomId}/join")
    public ResponseEntity<ChatRoom> joinChatRoom(@PathVariable Long roomId, @RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token.substring(7));  // "Bearer " 제거 후, extractUserId 사용
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        Long memberId = chatService.getMemberIdByUserId(userId); // JWT에서 가져온 userId로 memberId 조회
        return ResponseEntity.ok(chatService.joinChatRoom(roomId, memberId));
    }

    @PostMapping("/room/{roomId}/message")
    public ResponseEntity<ChatMessage> sendMessage(@PathVariable Long roomId, @RequestBody ChatMessageDTO chatMessageDTO, @RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token.substring(7));  // "Bearer " 제거 후, extractUserId 사용
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        chatMessageDTO.setSenderId(userId);  // JWT에서 가져온 userId를 설정
        return ResponseEntity.ok(chatService.sendMessage(roomId, chatMessageDTO));
    }

    // 인기 채팅방 엔드포인트
    @GetMapping("/popular-rooms")
    public ResponseEntity<List<ChatRoom>> getPopularChatRooms() {
        return ResponseEntity.ok(chatService.getPopularChatRooms());
    }

}
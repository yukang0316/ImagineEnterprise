package hello.imagine.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ChatMessageResponseDTO {
    private Long chatRoomId;
    private Long categoryId; // 카테고리 ID 추가
    private List<ChatMessageDTO> messages;

    public ChatMessageResponseDTO(Long chatRoomId, Long categoryId, List<ChatMessageDTO> messages) {
        this.chatRoomId = chatRoomId;
        this.categoryId = categoryId;
        this.messages = messages;
    }
}
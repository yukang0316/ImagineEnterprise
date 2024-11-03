package hello.imagine.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ChatMessageDTO {

    private Long id;
    private String content;
    private String senderId;
    private LocalDateTime timestamp;

    public ChatMessageDTO(Long id, String senderId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

}


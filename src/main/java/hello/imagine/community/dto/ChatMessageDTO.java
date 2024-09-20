package hello.imagine.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ChatMessageDTO {

    private String content;
    private String senderId;
    private LocalDateTime timestamp;

}
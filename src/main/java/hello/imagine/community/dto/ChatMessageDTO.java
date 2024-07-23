package hello.imagine.community.dto;

import java.time.LocalDateTime;

public class ChatMessageDTO {

    private String content;
    private Long senderId;
    private LocalDateTime timestamp;

    // getters and setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
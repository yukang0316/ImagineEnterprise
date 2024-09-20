package hello.imagine.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostDTO {
    private String title;
    private String content;
    private String authorId; // String으로 정의, Member ID를 저장
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
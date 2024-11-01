package hello.imagine.community.dto;

import hello.imagine.community.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorId; // String으로 정의, Member ID를 저장
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Post 엔티티를 인자로 받는 생성자 추가
    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorId = post.getAuthor().getId();  // Member ID 추출
        this.categoryId = post.getCategory().getId();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

}
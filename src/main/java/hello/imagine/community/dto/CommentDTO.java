package hello.imagine.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long categoryId; // 카테고리 ID
    private Long postId;    // 댓글이 달릴 게시글의 ID
    private String authorId;  // 댓글 작성자의 ID
    private String content; // 댓글 내용

    public CommentDTO() {
    }

    public CommentDTO(Long categoryId, Long postId, String authorId, String content) {
        this.categoryId = categoryId;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
    }
}
package hello.imagine.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long postId;    // 댓글이 달릴 게시글의 ID
    private Long authorId;  // 댓글 작성자의 ID
    private String content; // 댓글 내용

    public CommentDTO() {
    }

    public CommentDTO(Long postId, Long authorId, String content) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
    }
}
package hello.imagine.community.repository;

import hello.imagine.community.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 카테고리 ID로 게시글 조회
    List<Post> findByCategoryId(Long categoryId);
    // 제목이나 내용을 포함하는 게시글을 최신순으로 조회
    List<Post> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(String title, String content);
    List<Post> findTop10ByOrderByCreatedAtDesc();
    // 좋아요가 특정 개수 이상인 게시글을 최신순으로 조회
    List<Post> findByLikeCountGreaterThanEqualOrderByCreatedAtDesc(int likeCount);
}
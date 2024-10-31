package hello.imagine.community.repository;

import hello.imagine.community.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 카테고리 ID로 게시글 조회
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByTitleContainingOrContentContaining(String title, String content);
    List<Post> findTop10ByOrderByCreatedAtDesc();
    // 좋아요가 5개 이상인 게시글을 찾는 메서드
    List<Post> findByLikeCountGreaterThanEqual(int likeCount);
}
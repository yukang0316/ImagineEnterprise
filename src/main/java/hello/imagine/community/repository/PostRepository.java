package hello.imagine.community.repository;

import hello.imagine.community.model.Post;
import hello.imagine.login.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByTitleContainingOrContentContaining(String title, String content);
    List<Post> findTop10ByOrderByCreatedAtDesc();
    // 좋아요가 5개 이상인 게시글을 찾는 메서드
    List<Post> findByLikeCountGreaterThanEqual(int likeCount);
    List<Post> findByAuthor(Member author);
}
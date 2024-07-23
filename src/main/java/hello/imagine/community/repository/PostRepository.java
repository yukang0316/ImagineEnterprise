package hello.imagine.community.repository;

import hello.imagine.community.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByTitleContainingOrContentContaining(String title, String content);
    List<Post> findTop10ByOrderByCreatedAtDesc();
}
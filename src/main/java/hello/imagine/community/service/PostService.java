package hello.imagine.community.service;

import hello.imagine.community.dto.PostDTO;
import hello.imagine.community.model.Post;
import hello.imagine.community.model.Category;
import hello.imagine.community.repository.PostRepository;
import hello.imagine.community.repository.CategoryRepository;
import hello.imagine.login.model.Member;
import hello.imagine.login.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Post createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        Member author = memberRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author with ID " + postDTO.getAuthorId() + " not found"));
        post.setAuthor(author);

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category with ID " + postDTO.getCategoryId() + " not found"));
        post.setCategory(category);

        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Post updatePost(Long id, PostDTO postDTO) {
        Post post = getPostById(id);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUpdatedAt(postDTO.getUpdatedAt());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getPostsByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }

    public List<Post> searchPosts(String query) {
        return postRepository.findByTitleContainingOrContentContaining(query, query);
    }

    // 좋아요를 추가하거나 취소하는 메서드
    public boolean toggleLike(Long postId, String userId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }
        Post post = postOptional.get();

        // 좋아요를 누른 사용자 목록을 사용하여 좋아요 상태를 관리합니다.
        Set<String> likedByUsers = post.getLikedBy();

        if (likedByUsers.contains(userId)) {
            // 이미 좋아요를 눌렀다면, 좋아요 취소
            likedByUsers.remove(userId);
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
            return false; // 좋아요 취소됨
        } else {
            // 아직 좋아요를 누르지 않았다면, 좋아요 추가
            likedByUsers.add(userId);
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
            return true; // 좋아요 추가됨
        }
    }

    public int getLikeCount(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }
        Post post = postOptional.get();
        return post.getLikeCount();
    }

    // 좋아요가 5개 이상인 인기 게시글을 가져오는 메서드
    public List<Post> getPopularPosts() {
        return postRepository.findByLikeCountGreaterThanEqual(5);
    }

}
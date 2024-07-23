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

import java.util.List;

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

        Member author = memberRepository.findById(postDTO.getAuthor())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        post.setAuthor(author);

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        post.setCategory(category);

        post.setCreatedAt(postDTO.getCreatedAt());
        post.setUpdatedAt(postDTO.getUpdatedAt());
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

    public List<Post> getPopularPosts() {
        return postRepository.findTop10ByOrderByCreatedAtDesc();
    }
}
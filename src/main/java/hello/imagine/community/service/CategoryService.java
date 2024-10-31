package hello.imagine.community.service;

import hello.imagine.community.model.Category;
import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.community.repository.CategoryRepository;
import hello.imagine.community.repository.ChatRoomRepository;
import hello.imagine.community.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    // 고정된 카테고리 목록을 정의합니다.
    private final List<Category> fixedCategories = Arrays.asList(
            new Category(1L, "요리"),
            new Category(2L, "운동"),
            new Category(3L, "취업"),
            new Category(4L, "만화"),
            new Category(5L, "패션"),
            new Category(6L, "여행")
    );

    // 애플리케이션 시작 시 실행되는 메서드
    @PostConstruct
    public void init() {
        // 고정된 카테고리를 데이터베이스에 저장합니다.
        for (Category category : fixedCategories) {
            if (!categoryRepository.existsById(category.getId())) {
                categoryRepository.save(category);
            }
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 카테고리 ID로 게시글 목록 가져오는 메서드
    public List<Post> getPostsByCategoryId(Long categoryId) {
        // PostRepository를 이용해 해당 카테고리 ID로 게시글 가져오기
        return postRepository.findByCategoryId(categoryId);
    }

    // 카테고리 ID로 채팅방 목록 가져오는 메서드
    public List<ChatRoom> getChatRoomsByCategoryId(Long categoryId) {
        return chatRoomRepository.findByCategoryId(categoryId);
    }

    public Category findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.orElse(null);
    }
}

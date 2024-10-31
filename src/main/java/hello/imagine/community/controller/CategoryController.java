package hello.imagine.community.controller;

import hello.imagine.community.dto.ChatRoomDTO;
import hello.imagine.community.dto.PostDTO;
import hello.imagine.community.model.Category;
import hello.imagine.community.model.ChatRoom;
import hello.imagine.community.model.Post;
import hello.imagine.community.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // 카테고리 ID로 해당하는 게시글 목록 가져오기
    @GetMapping("/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long categoryId) {
        List<Post> posts = categoryService.getPostsByCategoryId(categoryId);
        List<PostDTO> postDTOs = posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
        Collections.reverse(postDTOs);
        return ResponseEntity.ok(postDTOs);
    }

    // 카테고리 ID로 해당하는 채팅방 목록 가져오기
    @GetMapping("/{categoryId}/chatrooms")
    public ResponseEntity<List<ChatRoomDTO>> getChatRoomsByCategory(@PathVariable Long categoryId) {
        List<ChatRoom> chatRooms = categoryService.getChatRoomsByCategoryId(categoryId);
        List<ChatRoomDTO> chatRoomDTOs = chatRooms.stream()
                .map(ChatRoomDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(chatRoomDTOs);
    }

}
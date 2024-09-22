package hello.imagine.community.controller;

import hello.imagine.community.dto.PostDTO;
import hello.imagine.community.model.Post;
import hello.imagine.community.service.PostService;
import hello.imagine.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;  // JWT 유틸리티 주입

    @Autowired
    private UserDetailsService customUserDetailsService;  // UserDetailsService 주입

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(HttpServletRequest request, @RequestBody PostDTO postDTO) {
        String token = getTokenFromRequest(request);

        // 토큰이 null인지, 그리고 토큰이 유효한지 검증
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 인증 실패
        }

        // 토큰에서 사용자 이름 추출
        String userId = jwtUtil.extractUserId(token);

        // UserDetailsService를 통해 UserDetails 객체 가져오기
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

        // 토큰 유효성 검증
        if (!jwtUtil.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 인증 실패
        }

        // 토큰이 유효하면 사용자 ID를 PostDTO에 설정
        postDTO.setAuthorId(userId);
        Post createdPost = postService.createPost(postDTO);

        return ResponseEntity.ok(createdPost);
    }

    // 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.updatePost(id, postDTO));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    // 카테고리별 게시글 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }

    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String query) {
        return ResponseEntity.ok(postService.searchPosts(query));
    }

    // 좋아요 기능: 추가 또는 취소
    @PostMapping("/{id}/like")
    public ResponseEntity<String> toggleLike(HttpServletRequest request, @PathVariable Long id) {
        String token = getTokenFromRequest(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // 토큰에서 사용자 ID 추출 및 검증
        String userId = jwtUtil.extractUserId(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

        if (!jwtUtil.validateToken(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }

        boolean liked = postService.toggleLike(id, userId);
        return ResponseEntity.ok(liked ? "좋아요가 추가되었습니다." : "좋아요가 취소되었습니다.");
    }

    // 좋아요 개수 가져오기
    @GetMapping("/{id}/like-count")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long id) {
        int likeCount = postService.getLikeCount(id);
        return ResponseEntity.ok(likeCount);
    }

    // JWT 토큰 추출 메서드
    private String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer "를 제거하고 토큰만 반환
        }
        return null;
    }
}
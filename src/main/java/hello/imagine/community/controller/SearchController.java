package hello.imagine.community.controller;

import hello.imagine.community.dto.PostDTO;
import hello.imagine.community.model.Post;
import hello.imagine.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    @Autowired
    private PostService postService;

    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam String query) {
        List<Post> posts = postService.searchPosts(query);
        List<PostDTO> postDTOs = posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }
}

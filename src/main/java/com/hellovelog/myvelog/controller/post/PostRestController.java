package com.hellovelog.myvelog.controller.post;

import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.PostDTO;
import com.hellovelog.myvelog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postapi")
public class PostRestController {

    private final PostService postService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.ok("게시물 삭제 완료.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("게시물 삭제 실패.");
        }
    }
    @GetMapping("/api/posts") //무한스크롤
    public Page<PostDTO> getPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "12") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postService.getAllPosts(pageable);
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<Map<String, Integer>> toggleLike(@PathVariable Long postId, Principal principal) {
        String username = principal.getName();
        int likeCount = postService.toggleLike(postId, username);
        Map<String, Integer> response = new HashMap<>();
        response.put("likeCount", likeCount); // 뷰에 반환
        return ResponseEntity.ok(response);
    }
}

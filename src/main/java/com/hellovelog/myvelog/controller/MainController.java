package com.hellovelog.myvelog.controller;

import com.hellovelog.myvelog.dto.PostDTO;
import com.hellovelog.myvelog.service.PostService;
import com.hellovelog.myvelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/myvelog")
    public String myvelog(Model model, Authentication authentication, Principal principal) {
        userService.setAuthentication(model, authentication);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        Page<PostDTO> postPage = postService.getAllPosts(pageable);
        setModelAttributes(model, postPage, 0, "내 벨로그");
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html 템플릿을 반환
    }

    @GetMapping("/trending")
    public String getTrendingPosts(Model model, @RequestParam(defaultValue = "0") int page, Authentication authentication) {
        userService.setAuthentication(model, authentication);
        Pageable pageable = PageRequest.of(page, 10, Sort.by("likeCount").descending());
        Page<PostDTO> postPage = postService.getTrendingPosts(LocalDateTime.now().minusWeeks(1), pageable);
        setModelAttributes(model, postPage, page, "트렌딩");
        return "main";
    }

    @GetMapping("/latest")
    public String getLatestPosts(Model model, @RequestParam(defaultValue = "0") int page, Authentication authentication) {
        userService.setAuthentication(model, authentication);
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<PostDTO> postPage = postService.getLatestPosts(pageable);
        setModelAttributes(model, postPage, page, "최신");
        return "main";
    }

    private void setModelAttributes(Model model, Page<PostDTO> postPage, int currentPage, String pageTitle) {
        model.addAttribute("postDTOs", postPage.getContent());
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageTitle", pageTitle);
    }
}

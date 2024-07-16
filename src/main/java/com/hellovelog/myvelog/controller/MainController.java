package com.hellovelog.myvelog.controller;

import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.PostDTO;
import com.hellovelog.myvelog.service.PostService;
import com.hellovelog.myvelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/myvelog")
    public String myvelog(Model model, Authentication authentication) {
        userService.setAuthentication(model, authentication);
        Pageable pageable = PageRequest.of(0, 10);
        Page<PostDTO> postPage = postService.getAllPosts(pageable);
        model.addAttribute("postDTOs", postPage.getContent());
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html 템플릿을 반환
    }

    @GetMapping("/trending")
    public String getTrendingPosts(Model model, @RequestParam(defaultValue = "0") int page,Authentication authentication) {
        userService.setAuthentication(model,authentication);
        Pageable pageable = PageRequest.of(page, 10);
        Page<PostDTO> postPage = postService.getTrendingPosts(LocalDateTime.now().minusWeeks(1), pageable);
        model.addAttribute("postDTOs", postPage.getContent());
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "main";
    }

    @GetMapping("/latest")
    public String getLatestPosts(Model model, @RequestParam(defaultValue = "0") int page,Authentication authentication) {
        userService.setAuthentication(model,authentication);
        Pageable pageable = PageRequest.of(page, 10);
        Page<PostDTO> postPage = postService.getLatestPosts(pageable);
        model.addAttribute("postDTOs", postPage.getContent());
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "main";
    }
}

package com.hellovelog.myvelog.controller;

import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.service.PostService;
import com.hellovelog.myvelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/myvelog")
    public String myvelog(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
            Optional<User> optionalUser = userService.getCurrentUser();
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("username", user.getUsername());
                System.out.println("===================================="+optionalUser.get().getUsername());

            }
        }
        model.addAttribute("postDTOs",postService.getAllPosts());
        return "main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html 템플릿을 반환
    }
}

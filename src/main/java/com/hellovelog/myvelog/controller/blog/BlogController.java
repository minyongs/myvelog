package com.hellovelog.myvelog.controller.blog;


import com.hellovelog.myvelog.domain.User;
import com.hellovelog.myvelog.dto.BlogDTO;
import com.hellovelog.myvelog.service.BlogService;
import com.hellovelog.myvelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/myvelog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final UserService userService;
    @GetMapping("/@{username}")
    public String getUserBlog(@PathVariable String username, Model model, Authentication authentication) {
        BlogDTO blogDTO = blogService.getUserBlog(username);

        model.addAttribute("blog", blogDTO);
        model.addAttribute("blogUsername", blogDTO.getUsername());

        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
            Optional<User> optionalUser = userService.getCurrentUser();
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("username", user.getUsername());
                System.out.println("====================================" + optionalUser.get().getUsername());
            }
        }

        return "myblogmain";
    }











}

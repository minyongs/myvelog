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
        userService.setAuthentication(model,authentication);
        model.addAttribute("blog", blogDTO);
        model.addAttribute("blogUsername", blogDTO.getUsername());


        return "myblogmain";
    }

}

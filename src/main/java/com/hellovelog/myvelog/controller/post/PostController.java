package com.hellovelog.myvelog.controller.post;


import com.hellovelog.myvelog.domain.Post;
import com.hellovelog.myvelog.dto.CommentDTO;
import com.hellovelog.myvelog.dto.PostDTO;
import com.hellovelog.myvelog.service.CommentService;
import com.hellovelog.myvelog.service.PostService;
import com.hellovelog.myvelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/myvelog")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;


    @GetMapping("/write")
    public String write(Model model, Principal principal) {
        model.addAttribute("post", new PostDTO());
        return "write";
    }

    @PostMapping("/write")
    public String writePost(@ModelAttribute PostDTO postDTO, Principal principal, @RequestParam String action) {
        String username = principal.getName();
        postDTO.setAuthor(username);

        if ("draft".equals(action)) {
            postService.saveTemporaryPost(username, postDTO);
        } else if ("publish".equals(action)) {
            postService.savePost(username, postDTO);
        }
        return "redirect:/myvelog/@" + username;
    }

    @GetMapping("/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model, Authentication authentication, Principal principal) {
        userService.setAuthentication(model, authentication);
        String user = principal.getName();
        model.addAttribute("loggedInUser", user);
        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("comments", commentService.getCommentsByPostId(id));
        return "postdetail";
    }

    @PostMapping("/detail/{id}/comments")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute CommentDTO commentDTO,
                             Principal principal) {
        commentService.addComment(id, commentDTO, principal.getName());
        return "redirect:/myvelog/detail/" + id;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        PostDTO postDTO = postService.getPostById(id);
        model.addAttribute("post",postDTO);
        return "editForm";

    }

    @PutMapping("/edit/{id}")
    public String editPost(@PathVariable Long id,@ModelAttribute PostDTO postDTO){
        postService.editPost(id,postDTO);
        return "redirect:/detail/" + id;
    }



}

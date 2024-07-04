package com.hellovelog.myvelog.controller;

import com.hellovelog.myvelog.dto.UserDTO;
import com.hellovelog.myvelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/myvelog")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerGet(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(UserDTO userDTO){
        userDTO.setRegistrationDate(LocalDateTime.now());
        boolean isSaved = userService.saveUser(userDTO);
        if (isSaved) {
            return "redirect:/login"; // 회원가입이 성공하면 로그인 페이지로 리다이렉트
        } else {
            return "register"; // 회원가입이 실패하면 다시 회원가입 페이지로 이동
        }
    }
}

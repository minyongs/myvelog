package com.hellovelog.myvelog.controller.user;

import com.hellovelog.myvelog.dto.UserDTO;
import com.hellovelog.myvelog.exception.customException.DuplicateUserException;
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
    public String registerUser(UserDTO userDTO, Model model) {
        try {
            userService.saveUser(userDTO);
            return "redirect:/login"; // 성공 시 리디렉션
        } catch (DuplicateUserException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}

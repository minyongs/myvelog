package com.hellovelog.myvelog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/myvelog")
    public String myvelog(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }
        return "main"; // main.html 템플릿을 반환
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html 템플릿을 반환
    }
}

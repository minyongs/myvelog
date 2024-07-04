package com.hellovelog.myvelog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myvelog")
public class MainController {

    @GetMapping
    public String main(){

        return "main";
    }

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }
}
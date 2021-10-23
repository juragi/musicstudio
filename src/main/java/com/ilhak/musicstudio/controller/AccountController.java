package com.ilhak.musicstudio.controller;

import com.ilhak.musicstudio.model.User;
import com.ilhak.musicstudio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String login(Model model) {
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.save(user);
        return "redirect:/";
    }
}

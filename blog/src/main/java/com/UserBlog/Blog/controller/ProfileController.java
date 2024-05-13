package com.UserBlog.Blog.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.UserBlog.Blog.service.UserService;
import com.UserBlog.Blog.model.User;
import org.springframework.ui.Model;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Optional<User> userOpt = userService.getCurrentUser();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            return "user/profile";
        } else {
            return "redirect:/login"; // Redirect to login if user is not authenticated
        }
    }
}

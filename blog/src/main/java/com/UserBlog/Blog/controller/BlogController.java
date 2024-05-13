package com.UserBlog.Blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.UserBlog.Blog.model.Post;
import com.UserBlog.Blog.model.User;
import com.UserBlog.Blog.service.PostService;
import com.UserBlog.Blog.service.UserService;

import java.util.List;

@Controller
public class BlogController {
    
    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
    private final PostService postService;

    @Autowired
    private UserService userService;

    public BlogController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }
    

    @GetMapping("/blog")
    public String showBlogPosts(Model model) {
        List<Post> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "blog";
    }    

    @GetMapping("/blogForm")
    public ModelAndView showBlogForm() {
        ModelAndView modelAndView = new ModelAndView("blogForm");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); 

        // Use the username to fetch your user details from your service
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        modelAndView.addObject("user", user);
        modelAndView.addObject("post", new Post());
        return modelAndView;
    }
      

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long postId, Model model) {
        return postService.findPostById(postId)
                          .map(post -> {
                              model.addAttribute("post", post);
                              logger.info("Displaying post with ID: {}", postId);
                              return "post";  
                          })
                          .orElse("error");  
    }
}

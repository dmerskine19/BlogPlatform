package com.UserBlog.Blog.controller;

import com.UserBlog.Blog.model.Post;
import com.UserBlog.Blog.service.PostService;
import com.UserBlog.Blog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);


    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    /**
     * Endpoint to create a post from form data, ensuring the author exists.
     * @param post the post to save, expecting title and content from form data.
     * @param authorId the ID of the author, expecting author_id from form data.
     * @return ResponseEntity with the created post or error message.
     */
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPostFromForm(@ModelAttribute Post post, @RequestParam("author_id") Long authorId) {
        logger.info("Received author_id: {}", authorId);
        return userService.findById(authorId).map(author -> {
            post.setAuthor(author);
            Post savedPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        }).orElse(ResponseEntity.notFound().build());
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        return postService.findPostById(id)
                .map(post -> {
                    postService.deletePost(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
package com.example.blogapp.controller;

import com.example.blogapp.dto.PostRequest;
import com.example.blogapp.dto.PostResponse;
import com.example.blogapp.model.Post;
import com.example.blogapp.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/by-category/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.getPostsByCategory(id));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostsByUsername(username));
    }

}

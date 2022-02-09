package com.example.blogapp.service;

import com.example.blogapp.dto.PostRequest;
import com.example.blogapp.dto.PostResponse;
import com.example.blogapp.exceptions.SpringBlogException;
import com.example.blogapp.mapper.PostMapper;
import com.example.blogapp.model.Category;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import com.example.blogapp.repository.CategoryRepository;
import com.example.blogapp.repository.PostRepository;
import com.example.blogapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public Post save(PostRequest postRequest) {
        Category category = categoryRepository.findByName(postRequest.getCategoryName())
                .orElseThrow(()-> new SpringBlogException("Category not found"));
        User currentUser = authService.getCurrentUser();

        postRepository.save(postMapper.map(postRequest, category, authService.getCurrentUser()));

       return postMapper.map(postRequest,category,currentUser);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringBlogException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }


    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new SpringBlogException("Category not found"));
        List<Post> posts = postRepository.findAllByCategory(category);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringBlogException("user not found"));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}

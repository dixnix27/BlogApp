package com.example.blogapp.mapper;
import com.example.blogapp.dto.PostRequest;
import com.example.blogapp.dto.PostResponse;
import com.example.blogapp.model.Category;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Category category, User user);

    @Mapping(target = "id" , source = "postId")
    @Mapping(target = "categoryName" , source = "category.name")
    @Mapping(target = "userName" , source = "user.username")
    PostResponse mapToDto(Post post);
}

package com.example.blogapp.mapper;
import com.example.blogapp.dto.CategoryDTO;
import com.example.blogapp.model.Category;
import com.example.blogapp.model.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper( componentModel = "spring")
public interface CategoryMapper {

@Mapping(target = "numberOfPosts", expression = "java(mapPosts(category.getPosts()))")
    CategoryDTO mapCategoryToDto(Category category);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts" , ignore = true)
    Category mapDtoToCategory(CategoryDTO categoryDTO);

}

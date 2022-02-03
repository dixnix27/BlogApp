package com.example.blogapp.service;

import com.example.blogapp.dto.CategoryDTO;
import com.example.blogapp.model.Category;
import com.example.blogapp.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

//    @Transactional(readOnly = true)
    public CategoryDTO save(CategoryDTO categoryDTO){
       Category save =  categoryRepository.save(mapDtoToCategory(categoryDTO));
        categoryDTO.setId(save.getId());
        return categoryDTO;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapCategoryToDto)
                .collect(Collectors.toList());
    }

    private Category mapDtoToCategory(CategoryDTO categoryDTO) {
        return Category.builder().name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .build();
    }

    private CategoryDTO mapCategoryToDto(Category category) {
        return CategoryDTO.builder().name(category.getName())
                .id(category.getId())
                .numberOfPosts(category.getPosts().size())
                .build();
    }

}

package com.example.blogapp.service;

import com.example.blogapp.dto.CategoryDTO;
import com.example.blogapp.exceptions.SpringBlogException;
import com.example.blogapp.mapper.CategoryMapper;
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
    private final CategoryMapper categoryMapper;

//    @Transactional(readOnly = true)
    public CategoryDTO save(CategoryDTO categoryDTO){
       Category save =  categoryRepository.save(categoryMapper.mapDtoToCategory(categoryDTO));
        categoryDTO.setId(save.getId());
        return categoryDTO;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToDto)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new SpringBlogException("No category found"));
        return categoryMapper.mapCategoryToDto(category);
    }
}

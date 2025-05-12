package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryDTO categoryDto);

    PageResult select(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteCategory(Long id);

    void startOrStop(Integer status, Long id);

    void updateCategory(CategoryDTO categoryDto);

    List<Category> selectByType(Integer type);
}

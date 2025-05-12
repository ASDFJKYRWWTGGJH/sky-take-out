package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("admin/category")
@Slf4j
@Api("分类接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDto) {
        log.info("新增分类:{}",categoryDto.toString());
        categoryService.addCategory(categoryDto);
        return Result.success();
    }

}

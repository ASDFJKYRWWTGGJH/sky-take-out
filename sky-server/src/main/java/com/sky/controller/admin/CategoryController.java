package com.sky.controller.admin;

import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/category")
@RestController
@Slf4j
@Api("分类接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDto) {
        System.out.println("当前用户ID：" + BaseContext.getCurrentId());
        log.info("新增分类:{}",categoryDto.toString());
        categoryService.addCategory(categoryDto);
        return Result.success();
    }

    @GetMapping("page")
    @ApiOperation("分页查询菜品")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询菜品",categoryPageQueryDTO);
        PageResult result = categoryService.select(categoryPageQueryDTO);
        return Result.success(result);
    }
    @DeleteMapping
    @ApiOperation("根据id删除菜品分类")
    public Result deleteCategory(Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
    @ApiOperation("修改状态")
    @PostMapping("status/{status}")
    public Result startOrStopCategory(@PathVariable("status") Integer status,Long id) {
        categoryService.startOrStop(status,id);
        return Result.success();
    }
    @ApiOperation("修改分类菜品信息")
    @PutMapping
    public Result updateCategory(@RequestBody CategoryDTO categoryDto) {
        categoryService.updateCategory(categoryDto);
        return Result.success();
    }

    @ApiOperation("根据类型查询分类")
    @GetMapping("/list")
    public Result<List<Category>>selectByType(Integer type){
        List<Category> list = categoryService.selectByType(type);
        return Result.success(list);
    }


}

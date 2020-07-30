package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsCategoryService;
import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.result.Category;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/28 10:41
 */
/*

 */
@RestController
@RequestMapping("/category")
public class GoodsCategoryController {
    @Reference
    private IGoodsCategoryService goodsCategoryService;

    //分页查询类目信息
    @GetMapping
    public ResponseEntity<PageResult<GoodsCategory>> query(Integer currentPage, Integer pageSize, GoodsCategory goodsCategory) {
        if (currentPage == null || pageSize == null) {
            currentPage = 1;
            pageSize = 10;
        }

        //调用远程服务完成分页查询
        PageResult<GoodsCategory> pageResult = goodsCategoryService.query(currentPage, pageSize, goodsCategory);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    //根据id查询类目信息
    @GetMapping("/{id}")
    public ResponseEntity<Category> queryById(@PathVariable("id") Long id) {
        Category category = goodsCategoryService.queryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    //保存类目信息
    @PostMapping
    public ResponseEntity save(@RequestBody Category category) {
        int result = goodsCategoryService.save(category);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //更新目录信息
    @PutMapping
    public ResponseEntity update(Category category) {
        int result = goodsCategoryService.update(category);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //删除类目信息
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        int result = goodsCategoryService.delete(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

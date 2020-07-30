package com.it.yanxuan.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsCategoryService;
import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.result.Category;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/8 15:05
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Reference
    private IGoodsCategoryService goodsCategoryService;


    /*@GetMapping
    public ResponseEntity<PageResult<GoodsCategory>> query(GoodsCategory goodsCategory) {
        List<GoodsCategory> pageResult = goodsCategoryService.queryAllCategory(goodsCategory);
        return new ResponseEntity(pageResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> queryBrand(@PathVariable("id") Long id) {
        Category category = goodsCategoryService.qeuryById(id);
        return new ResponseEntity(category, HttpStatus.OK);
    }*/
    @GetMapping
    public ResponseEntity<PageResult<GoodsCategory>> query(Integer currentPage, Integer pageSize, GoodsCategory goodsCategory){
        // 处理分页参数
        if(currentPage ==null || pageSize ==null){
            currentPage = 1;
            pageSize = Integer.MAX_VALUE;
        }
        // 调用远程的服务进行类目信息的查询
        PageResult<GoodsCategory> pageResult = goodsCategoryService.pageQuery(currentPage, pageSize, goodsCategory);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> queryById(@PathVariable("id") Long id){
        // 根据主键进行查询
        Category category = goodsCategoryService.queryById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}

package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsSpecService;
import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.Specification;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/27 12:39
 */
@RestController
@RequestMapping("/spec")
public class GoodsSpecController {

    @Reference
    private IGoodsSpecService goodsSpecService;

    //根据条件进行分页查询
    @GetMapping
    public ResponseEntity<PageResult<GoodsSpec>> query(Integer currentPage, Integer pageSize, GoodsSpec goodsSpec) {
        //分页参数的判断
        if (currentPage == null || pageSize == null) {
            currentPage = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //查询
        PageResult<GoodsSpec> goodsSpecPageResult = goodsSpecService.pageQuery(currentPage,pageSize,goodsSpec);
        return new ResponseEntity<PageResult<GoodsSpec>>(goodsSpecPageResult, HttpStatus.OK);
    }

    //保存规格信息
    @PostMapping
    public ResponseEntity save(@RequestBody Specification specification) {
        int result = goodsSpecService.save(specification);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    //根据Specification中的信息更新规格信息
    @PutMapping
    public ResponseEntity update(@RequestBody Specification specification) {
        int result = goodsSpecService.update(specification);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //根据id查询规格信息
    @GetMapping("/{id}")
    public ResponseEntity<Specification> selectById(@PathVariable("id") Long id) {
        Specification specification = goodsSpecService.selectById(id);
        return new ResponseEntity<>(specification, HttpStatus.OK);
    }

    //删除规格信息
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        int result = goodsSpecService.delete(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

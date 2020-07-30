package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsInfoService;
import com.it.yanxuan.model.GoodsSpu;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.GoodsInfo;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: cyb
 * @create: 2020/7/17 14:54
 */
@RestController
@RequestMapping("/goods")
public class GoodsInfoController {
    @Reference
    private IGoodsInfoService goodsInfoService;

    @GetMapping
    public ResponseEntity<PageResult<GoodsSpu>> query(Integer currentPage, Integer pageSize, GoodsSpu goodsSpu) {
        if (currentPage == null || pageSize == null) {
            currentPage = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //调用远程服务进行分页查询
        PageResult<GoodsSpu> pageResult = goodsInfoService.pageQuery(currentPage, pageSize, goodsSpu);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodsInfo> queryById(@PathVariable("id") Long id) {
        GoodsInfo goodsInfo = goodsInfoService.queryById(id);
        return new ResponseEntity<>(goodsInfo, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity auditGoods(@RequestBody GoodsSpu goodsSpu) {
        int result = goodsInfoService.audit(goodsSpu);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        int result = goodsInfoService.delete(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.it.yanxuan.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.goods.api.IGoodsInfoService;
import com.it.yanxuan.model.GoodsSpu;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.GoodsInfo;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/8 14:44
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsInfoService goodsInfoService;

    @PostMapping
    public ResponseEntity save(@RequestBody GoodsInfo goodsInfo) {
        //获取当前登录的账号姓名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        goodsInfo.setCreatePerson(name);
        //调用远程服务完成商品信息的保存
        int result = goodsInfoService.save(goodsInfo);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<PageResult<GoodsSpu>> query(Integer currentPage, Integer pageSize, GoodsSpu goodsSpu) {
        //分页参数的处理
        if (currentPage == null || pageSize == null) {
            currentPage = 1;
            pageSize = Integer.MAX_VALUE;
        }
        //获取当前登陆人
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        goodsSpu.setCreatePerson(username);
        //调用远程服务机型查询
        PageResult<GoodsSpu> pageResult = goodsInfoService.pageQuery(currentPage, pageSize, goodsSpu);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodsInfo> queryById(@PathVariable("id") Long id) {
        GoodsInfo goodsInfo = goodsInfoService.queryById(id);
        return new ResponseEntity<>(goodsInfo, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GoodsInfo> update(@RequestBody GoodsInfo goodsInfo) {
        //获取当前登陆人
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        goodsInfo.setUpdatePerson(username);
        //调用远程服务进行修改
        int result = goodsInfoService.update(goodsInfo);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping
    public ResponseEntity<GoodsSpu> putOnShelf(@RequestBody GoodsInfo goodsInfo) {
        int result = goodsInfoService.putOnShelf(goodsInfo);
        if (result > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

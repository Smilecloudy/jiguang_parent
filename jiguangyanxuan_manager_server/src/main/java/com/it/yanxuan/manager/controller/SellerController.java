package com.it.yanxuan.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.SellerInfo;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.seller.api.ISellerInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/7 15:13
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private ISellerInfoService sellerInfoService;

    @GetMapping
    public ResponseEntity<PageResult> query(Integer currentPage, Integer pageSize, SellerInfo sellerInfo) {
        if (currentPage == null || pageSize == null) {
            currentPage = 1;
            pageSize = Integer.MAX_VALUE;
        }
        PageResult<SellerShop> pageResult = sellerInfoService.query(currentPage, pageSize, sellerInfo);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody SellerShop sellerShop) {
        int result = sellerInfoService.update(sellerShop);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

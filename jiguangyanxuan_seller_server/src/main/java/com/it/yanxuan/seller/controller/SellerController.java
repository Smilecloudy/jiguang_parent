package com.it.yanxuan.seller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.SellerInfo;
import com.it.yanxuan.seller.api.ISellerInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/7 12:43
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private ISellerInfoService sellerInfoService;


    //保存商家入驻信息
    @PostMapping
    public ResponseEntity save(@RequestBody SellerInfo sellerInfo) {
        int result = sellerInfoService.save(sellerInfo);
        if (result>0) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

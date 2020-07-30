package com.it.yanxuan.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.ad.api.IAdInfoService;
import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: cyb
 * @create: 2020/7/19 16:02
 */
@RestController
@RequestMapping("/adInfo")
public class AdInfoController {

    @Reference
    private IAdInfoService adInfoService;

    @GetMapping
    public ResponseEntity<PageResult<AdInfo>> query(AdInfo adInfo) {
        Integer currentPage = 1;
        Integer pageSize = Integer.MAX_VALUE;
        PageResult<AdInfo> pageResult = adInfoService.pageQuery(currentPage, pageSize, adInfo);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);

    }

}

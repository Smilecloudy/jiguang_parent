package com.it.yanxuan.ad.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.ad.api.IAdInfoService;
import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: cyb
 * @create: 2020/7/17 20:25
 */
@RestController
@RequestMapping("/adInfo")
public class AdInfoController {
    @Reference
    private IAdInfoService adInfoService;

    @GetMapping
    public ResponseEntity<PageResult<AdInfo>> query(Integer currentPage, Integer pageSize, AdInfo adInfo) {
        if (currentPage == null || pageSize == null) {
            currentPage =1;
            pageSize = Integer.MAX_VALUE;
        }
        PageResult<AdInfo> pageResult = adInfoService.pageQuery(currentPage, pageSize, adInfo);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AdInfo adInfo) {
        int result = adInfoService.save(adInfo);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody AdInfo adInfo) {
        int result = adInfoService.update(adInfo);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        int result = adInfoService.deleteById(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdInfo> queryById(@PathVariable Long id) {
        AdInfo adInfo = adInfoService.queryById(id);
        return new ResponseEntity<>(adInfo, HttpStatus.OK);
    }
}

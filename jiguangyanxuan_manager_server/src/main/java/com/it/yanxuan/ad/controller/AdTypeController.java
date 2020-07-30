package com.it.yanxuan.ad.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.ad.api.IAdTypeService;
import com.it.yanxuan.model.AdType;
import com.it.yanxuan.result.PageResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @auther: cyb
 * @create: 2020/7/17 17:45
 */
@RestController
@RequestMapping("/adType")
public class AdTypeController {
    @Reference
    private IAdTypeService adTypeService;

    @GetMapping
    public ResponseEntity<PageResult<AdType>> query(Integer currentPage, Integer pageSize, AdType adType) {
        if (currentPage == null || pageSize == null) {
            currentPage =1;
            pageSize = Integer.MAX_VALUE;
        }
        PageResult<AdType> pageResult = adTypeService.query(currentPage, pageSize, adType);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AdType adType) {
        int result = adTypeService.save(adType);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody AdType adType) {
        int result = adTypeService.update(adType);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        int result = adTypeService.delete(id);
        if (result > 0) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

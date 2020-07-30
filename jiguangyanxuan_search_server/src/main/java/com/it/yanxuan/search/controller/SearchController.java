package com.it.yanxuan.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.it.yanxuan.search.api.ISearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @auther: cyb
 * @create: 2020/7/21 16:33
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Reference
    private ISearchService searchService;

    @GetMapping
    public ResponseEntity<Map<String,Object>> query(@RequestParam Map queryParams) {
        //调用远程服务
        Map<String,Object> resultMap = searchService.query(queryParams);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}

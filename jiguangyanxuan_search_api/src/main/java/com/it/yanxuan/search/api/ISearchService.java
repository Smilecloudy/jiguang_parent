package com.it.yanxuan.search.api;

import java.util.Map;

/**
 * @auther: cyb
 * @create: 2020/7/21 16:24
 */
public interface ISearchService {
    //根据条件查询Solr服务器中的信息
    Map<String, Object> query(Map queryParams);
}

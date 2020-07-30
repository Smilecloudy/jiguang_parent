package com.it.yanxuan.ad.api;

import com.it.yanxuan.model.AdType;
import com.it.yanxuan.result.PageResult;

/**
 * @auther: cyb
 * @create: 2020/7/17 17:19
 */
public interface IAdTypeService {
    //广告分页查询
    PageResult<AdType> query(Integer currentPage, Integer pageSize, AdType adType);

    //新增广告保存
    int save(AdType adType);

    //修改广告
    int update(AdType adType);

    int delete(Long id);
}

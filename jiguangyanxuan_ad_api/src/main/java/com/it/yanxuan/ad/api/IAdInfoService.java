package com.it.yanxuan.ad.api;

import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.result.PageResult;

/**
 * @auther: cyb
 * @create: 2020/7/17 17:20
 */
public interface IAdInfoService {
    //广告信息分页查询
    PageResult<AdInfo> pageQuery(Integer currentPage, Integer pageSize, AdInfo adInfo);

    //保存
    int save(AdInfo adInfo);

    //更新
    int update(AdInfo adInfo);


    //删除
    //int delete(Long id);
    int deleteById(Long id);

    //根据id查询
    AdInfo queryById(Long id);

}

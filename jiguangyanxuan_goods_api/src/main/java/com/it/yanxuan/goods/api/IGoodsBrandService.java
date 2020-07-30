package com.it.yanxuan.goods.api;

import com.it.yanxuan.model.GoodsBrand;
import com.it.yanxuan.result.PageResult;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/23 13:43
 */
/*
    商品列表的服务接口
 */
public interface IGoodsBrandService {

    //查询所有商品品牌
    public List<GoodsBrand> queryAll();

    //根据分页参数进行分页查询
    public PageResult<GoodsBrand> pageQuery(Integer currentPage, Integer pageSize);

    //根据查询条件、分页参数进行条件分页查询
    public PageResult<GoodsBrand> pageQuery(Integer currentPage, Integer pageSize, GoodsBrand goodsBrand);

    //将新增的品牌信息保存到数据库
    public int save(GoodsBrand goodsBrand);

    //将修改的品牌信息保存到数据库
    public int update(GoodsBrand goodsBrand);

    //根据品牌的id从数据库删除品牌信息
    public int deleteByBrandId(Long id);


    //通过id查询品牌信息
    public GoodsBrand queryById(Long id);
}

package com.it.yanxuan.goods.api;

import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.Specification;
import com.it.yanxuan.result.PageResult;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/27 12:40
 */

public interface IGoodsSpecService {

    //根据条件进行分页查询
    public PageResult<GoodsSpec> pageQuery(Integer currentPage, Integer pageSize, GoodsSpec goodsSpec);

    //保存规格信息
    public int save(Specification specification);

    //根据id查询规格信息
    public Specification selectById(Long id);

    //根据id删除规格信息
    public int delete(Long id);


    //根据Specification中的信息更新规格信息
    public int update(Specification specification);

}

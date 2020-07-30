package com.it.yanxuan.goods.api;

import com.it.yanxuan.model.GoodsSpu;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.GoodsInfo;
import com.it.yanxuan.result.PageResult;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/8 14:45
 */

public interface IGoodsInfoService {

    //保存商品的SPU信息
    public int save(GoodsInfo goodsInfo);

    //根据名字和状态信息进行分页查询
    public PageResult<GoodsSpu> pageQuery(Integer currentPage, Integer pageSize, GoodsSpu goodsSpu);

    //根据id查询商品信息
    public GoodsInfo queryById(Long id);

    //更新商品信息
    public int update(GoodsInfo goodsInfo);

    int audit(GoodsSpu goodsSpu);

    //删除商品
    int delete(Long id);

    //上架商品，
    int putOnShelf(GoodsInfo goodsInfo);
}

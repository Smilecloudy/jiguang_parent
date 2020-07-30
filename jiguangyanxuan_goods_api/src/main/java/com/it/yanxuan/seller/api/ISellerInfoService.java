package com.it.yanxuan.seller.api;

import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.SellerInfo;
import com.it.yanxuan.result.PageResult;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/7 12:47
 */
public interface ISellerInfoService {

    //保存商家入驻信息
    public int save(SellerInfo sellerInfo);

    //查询商家审核信息
    public PageResult<SellerShop> query(Integer currentPage, Integer pageSize, SellerInfo sellerInfo);

    //根据id修改状态码
    public int update(SellerShop sellerShop);
}

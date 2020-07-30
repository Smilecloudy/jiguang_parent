package com.it.yanxuan.model.com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.GoodsSku;
import com.it.yanxuan.model.GoodsSpu;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/9 15:55
 */
public class GoodsInfo extends GoodsSpu {
    private List<GoodsSku> skuList;

    public List<GoodsSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<GoodsSku> skuList) {
        this.skuList = skuList;
    }
}

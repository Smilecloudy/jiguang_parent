package com.it.yanxuan.result;

import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.model.GoodsCategoryBrandSpec;

import java.util.List;
import java.util.Map;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/29 10:23
 */
/*
    封装类目的相关信息
 */
public class Category extends GoodsCategory {
    //关联品牌、规格信息
    private GoodsCategoryBrandSpec relation;

    // 存储类目关联的规格和规格项信息
    private List<Map> specList;

    public List<Map> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Map> specList) {
        this.specList = specList;
    }

    public GoodsCategoryBrandSpec getRelation() {
        return relation;
    }

    public void setRelation(GoodsCategoryBrandSpec relation) {
        this.relation = relation;
    }
}

package com.it.yanxuan.model.com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.model.GoodsSpecOption;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/27 13:36
 */
/*
    用来封装规格的信息
 */
public class Specification extends GoodsSpec {
    private List<GoodsSpecOption> optionList;

    public List<GoodsSpecOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<GoodsSpecOption> optionList) {
        this.optionList = optionList;
    }
}

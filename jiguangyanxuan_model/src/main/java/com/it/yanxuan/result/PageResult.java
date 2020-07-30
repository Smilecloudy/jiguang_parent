package com.it.yanxuan.result;

import java.io.Serializable;
import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/24 11:54
 */
/*
    封装响应的分页结果
 */
public class PageResult<T> implements Serializable {
    private Long total;//总记录数
    private List<T> result;//当前页所有数据

    public PageResult() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}

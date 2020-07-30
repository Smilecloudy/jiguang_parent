package com.it.yanxuan.goods.api;

import com.it.yanxuan.model.GoodsCategory;
import com.it.yanxuan.result.Category;
import com.it.yanxuan.result.PageResult;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/28 10:43
 */
public interface IGoodsCategoryService {

    //查询所有category
    public PageResult<GoodsCategory> query(Integer currentPage, Integer pageSize, GoodsCategory goodsCategory);

    //查询所有类目
    public List<GoodsCategory> queryAllCategory(GoodsCategory goodsCategory);

    //新增父级类目、子级类目的保存
    public int save(Category category);

    //根据id查询类目信息
    public Category queryById(Long id);

    //更新目录信息
    public int update(Category category);

    //根据id删除类目信息
    public int delete(Long id);

    //根据条件进行分页查询
    public PageResult<GoodsCategory> pageQuery(Integer currentPage, Integer pageSize, GoodsCategory goodsCategory);

}

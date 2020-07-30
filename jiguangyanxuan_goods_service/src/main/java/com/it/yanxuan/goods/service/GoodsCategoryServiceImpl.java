package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.goods.api.IGoodsCategoryService;
import com.it.yanxuan.mapper.GoodsCategoryBrandSpecMapper;
import com.it.yanxuan.mapper.GoodsCategoryMapper;
import com.it.yanxuan.mapper.GoodsSpecOptionMapper;
import com.it.yanxuan.model.*;
import com.it.yanxuan.result.Category;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/28 10:42
 */
@Service
@Transactional
public class GoodsCategoryServiceImpl implements IGoodsCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsCategoryBrandSpecMapper goodsCategoryBrandSpecMapper;

    @Autowired
    private GoodsSpecOptionMapper goodsSpecOptionMapper;


    @Override
    public PageResult<GoodsCategory> query(Integer currentPage, Integer pageSize, GoodsCategory goodsCategory) {
        GoodsCategoryExample goodsCategoryExample = new GoodsCategoryExample();

        if (goodsCategory != null && !"".equals(goodsCategory.getName())) {
            goodsCategoryExample.createCriteria().andNameLike("%" + goodsCategory.getName() + "%");
        } else {
            if (goodsCategory.getParentId() != null) {
                //当没有查询条件时，添加根据parentId进行查询的条件，只查询第一级类目
                goodsCategoryExample.or().andParentIdEqualTo(goodsCategory.getParentId());
            }
        }

        if (currentPage == null || pageSize == null) {
            currentPage = 1;
            pageSize = Integer.MAX_VALUE;
        }

        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        //查询
        Page<GoodsCategory> categoryList = (Page<GoodsCategory>) goodsCategoryMapper.selectByExample(goodsCategoryExample);
        //构建返回结果
        PageResult<GoodsCategory> pageResult = new PageResult<>();
        pageResult.setTotal(categoryList.getTotal());
        pageResult.setResult(categoryList.getResult());

        return pageResult;
    }

    @Override
    public List<GoodsCategory> queryAllCategory(GoodsCategory goodsCategory) {
        GoodsCategoryExample goodsCategoryExample = new GoodsCategoryExample();
        goodsCategoryExample.or().andParentIdEqualTo(goodsCategory.getParentId());
        List<GoodsCategory> categoryList = goodsCategoryMapper.selectByExample(goodsCategoryExample);
        return categoryList;
    }

    //根据id查询类目信息
    @Override
    public Category queryById(Long id) {

        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(id);
        //查询关联信息
        GoodsCategoryBrandSpecExample goodsCategoryBrandSpecExample = new GoodsCategoryBrandSpecExample();
        goodsCategoryBrandSpecExample.or().andCategoryIdEqualTo(id);
        List<GoodsCategoryBrandSpec> relationList = goodsCategoryBrandSpecMapper.selectByExample(goodsCategoryBrandSpecExample);
        //构建返回结果
        Category category = new Category();
        BeanUtils.copyProperties(goodsCategory, category);
        if (relationList.size() > 0) {
            //关联的品牌和规格信息
            category.setRelation(relationList.get(0));
            //获取规格信息
            //[{"id":11,"name":"分辨率"},{"id":12,"name":"屏幕尺寸"},{"id":13,"name":"处理器"},{"id":17,"name":"电脑硬盘大小"}]
            String specIds = relationList.get(0).getSpecIds();
            //将JSON格式转换成
            List<Map> mapList = JSON.parseArray(specIds, Map.class);
            for (Map temMap : mapList) {
                //获取规格的id
                Long specId = new Long((Integer) temMap.get("id"));
                //创建查询条件
                GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
                goodsSpecOptionExample.createCriteria().andSpecIdEqualTo(specId);
                //进行查询
                List<GoodsSpecOption> optionList = goodsSpecOptionMapper.selectByExample(goodsSpecOptionExample);
                temMap.put("optionList", optionList);
            }
            //规格和规格项信息
            category.setSpecList(mapList);
        }
        return category;
    }

    //新增父级类目、子级类目的保存
    @Override
    public int save(Category category) {
        //设置信息为有效
        category.setStatus("0");
        GoodsCategoryExample goodsCategoryExample = new GoodsCategoryExample();
        //设置排序序号时，得先查询数据库中该类目的子类目存在的数量
        goodsCategoryExample.or().andParentIdEqualTo(category.getParentId());
        int count = (int) goodsCategoryMapper.countByExample(goodsCategoryExample);
        category.setSortNo(count + 1);
        //保存类目信息
        int result = goodsCategoryMapper.insertSelective(category);

        //保存关联的品牌信息，规格信息
        GoodsCategoryBrandSpec relation = category.getRelation();
        relation.setCategoryId(category.getId());
        goodsCategoryBrandSpecMapper.insert(relation);
        return result;
    }

    //更新目录信息
    @Override
    public int update(Category category) {
        //更新类目信息
        int result = goodsCategoryMapper.updateByPrimaryKey(category);
        //更新类目关联信息
        GoodsCategoryBrandSpec relation = category.getRelation();
        goodsCategoryBrandSpecMapper.updateByPrimaryKey(relation);

        return result;
    }

    @Override
    public PageResult<GoodsCategory> pageQuery(Integer currentPage, Integer pageSize, GoodsCategory goodsCategory) {
        //构建查询条件
        GoodsCategoryExample goodsCategoryExample = new GoodsCategoryExample();
        if (goodsCategory != null) {
            if (goodsCategory.getName() != null && !"".equals(goodsCategory.getName())) {
                goodsCategoryExample.createCriteria().andNameLike(goodsCategory.getName());
            } else {
                //否则使用parentId进行查询
                if (goodsCategory.getParentId() != null) {
                    goodsCategoryExample.createCriteria().andParentIdEqualTo(goodsCategory.getParentId());
                }
            }
        }
        //开启分页
        PageHelper.startPage(currentPage,pageSize);
        Page<GoodsCategory> categoryList = (Page<GoodsCategory>) goodsCategoryMapper.selectByExample(goodsCategoryExample);
        //构建返回结果
        PageResult<GoodsCategory> pageResult = new PageResult<>();
        pageResult.setResult(categoryList.getResult());
        pageResult.setTotal(categoryList.getTotal());
        return null;
    }

    @Override
    public int delete(Long id) {
        Category category = new Category();
        category.setId(id);
        category.setStatus("1");
        int result = goodsCategoryMapper.updateByPrimaryKey(category);
        return result;
    }
}

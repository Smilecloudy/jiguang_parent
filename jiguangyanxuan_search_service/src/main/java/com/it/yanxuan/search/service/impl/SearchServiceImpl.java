package com.it.yanxuan.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.it.yanxuan.mapper.GoodsCategoryBrandSpecMapper;
import com.it.yanxuan.mapper.GoodsCategoryMapper;
import com.it.yanxuan.mapper.GoodsSpecOptionMapper;
import com.it.yanxuan.model.*;
import com.it.yanxuan.search.api.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: cyb
 * @create: 2020/7/21 16:24
 */
@Service
//@Transactional
public class SearchServiceImpl implements ISearchService {
    @Autowired
    private SolrTemplate solrTemplate;

    //根据条件查询Solr服务器中的信息
    @Override
    public Map<String, Object> query(Map queryParams) {
        //创建返回的最终结果
        Map<String, Object> result = new HashMap<>();

        //根据关键词查询最新商品
        this.queryGoods(queryParams, result);
        //根据关键字查询商品的类目信息
        this.queryCategory(queryParams, result);
        //根据类目信息查询品牌和规格信息
        this.queryBrandAndSpec(result);
        return result;
    }

    /**
     * 根据关键词进行商品查询并将关键词进行高亮显示
     *
     * @param queryParams
     * @param resultMap
     */
    public void queryGoods(Map queryParams, Map resultMap) {
        //获取查询参数
        String keyword = (String) queryParams.get("keywords");
        //创建条件
        Criteria criteria = new Criteria("goods_keywords").contains(keyword);
        //创建查询条件
        SimpleHighlightQuery shq = new SimpleHighlightQuery(criteria);
        //创建高亮显示操作
        HighlightOptions options = new HighlightOptions();
        options.addField("goods_label");
        //设置高亮前缀
        options.setSimplePrefix("<em style='color:red;'>");
        options.setSimplePostfix("</em>");
        //将高亮options加入到查询条件
        shq.setHighlightOptions(options);
        //高亮查询
        HighlightPage<GoodsSpu> highlightPage = solrTemplate.queryForHighlightPage(shq, GoodsSpu.class);

        //构建返回结果
        ArrayList<GoodsSpu> resultList = new ArrayList<>();
        //获取返回结果
        List<HighlightEntry<GoodsSpu>> entryList = highlightPage.getHighlighted();
        for (HighlightEntry<GoodsSpu> entry : entryList) {
            //获取文档数据
            GoodsSpu entity = entry.getEntity();
            //获取高亮信息，包含field、和高亮片段
            List<HighlightEntry.Highlight> highlights = entry.getHighlights();
            for (HighlightEntry.Highlight highlight : highlights) {
                List<String> snipplets = highlight.getSnipplets();
                StringBuffer stringBuffer = new StringBuffer();
                for (String str : snipplets) {
                    stringBuffer.append(str);
                }
                entity.setLabel(stringBuffer.toString());

            }
            resultList.add(entity);
        }
        resultMap.put("result", resultList);
        resultMap.put("total", highlightPage.getTotalElements());
        resultMap.put("keywords", keyword);
    }

    public void queryCategory(Map queryParams, Map resultMap) {
        //创建一个集合存储类目信息
        ArrayList<String> categoryList = new ArrayList<>();
        //获取查询参数
        String keyword = (String) queryParams.get("keywords");
        //创建查询条件
        Criteria criteria = new Criteria("goods_keywords").is(keyword);
        SimpleQuery query = new SimpleQuery(criteria);
        //设置分组的相关操作
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("goods_category");
        query.setGroupOptions(groupOptions);
        //执行分组的查询
        GroupPage<GoodsSpu> groupPage = solrTemplate.queryForGroupPage(query, GoodsSpu.class);
        //根据fieldName获取分页结果
        GroupResult<GoodsSpu> groupResult = groupPage.getGroupResult("goods_category");
        //获取分组的实体对象
        Page<GroupEntry<GoodsSpu>> groupEntryPage = groupResult.getGroupEntries();
        //具体的分页信息
        List<GroupEntry<GoodsSpu>> groupEntryList = groupEntryPage.getContent();
        for (GroupEntry<GoodsSpu> groupEntry : groupEntryList) {
            String groupValue = groupEntry.getGroupValue();
            categoryList.add(groupValue);
        }
        resultMap.put("category", categoryList);
    }

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private GoodsCategoryBrandSpecMapper goodsCategoryBrandSpecMapper;
    @Autowired
    private GoodsSpecOptionMapper goodsSpecOptionMapper;
    /*
    查询关联的品牌信息和规格信息
    1.从redis数据，根据一级类目》二级类目》三级类目，查找对应的value值（主键id）
    2.根据类目的主键id查询关联的品牌和规格信息
     */
    public void queryBrandAndSpec(Map resultMap) {
        //获取类目信息
        List<String> categoryList = (List<String>) resultMap.get("category");
        //读取第一个类目信息
        if (categoryList.size() > 0) {
            String category = categoryList.get(0);
            //从redis数据库中查找
            Integer categoryId = (Integer) redisTemplate.boundHashOps("category").get(category);
            if (categoryId == null) {
                //从数据库中查询数据并存放到redis中
                //创建查询三级类目的条件
                GoodsCategoryExample goodsCategoryExample = new GoodsCategoryExample();
                goodsCategoryExample.createCriteria().andLevelEqualTo(3);
                //执行查询
                List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.selectByExample(goodsCategoryExample);

                HashMap<String, Long> categoryMap = new HashMap<>();
                for (GoodsCategory goodsCategory : goodsCategoryList) {
                    categoryMap.put(goodsCategory.getStructName() + ">" + goodsCategory.getName(), goodsCategory.getId());
                }
                //保存数据到redis
                redisTemplate.boundHashOps("categoryList").putAll(categoryMap);

                categoryId = categoryMap.get(category).intValue();
            }
            //查询关联的信息
            GoodsCategoryBrandSpecExample goodsCategoryBrandSpecExample = new GoodsCategoryBrandSpecExample();
            goodsCategoryBrandSpecExample.createCriteria().andCategoryIdEqualTo(new Long(categoryId));
            List<GoodsCategoryBrandSpec> goodsCategoryBrandSpecs = goodsCategoryBrandSpecMapper.selectByExample(goodsCategoryBrandSpecExample);
            GoodsCategoryBrandSpec goodsCategoryBrandSpec = goodsCategoryBrandSpecs.get(0);
            //获取品牌信息
            String brandIds = goodsCategoryBrandSpec.getBrandIds();
            List<Map> brandList = JSON.parseArray(brandIds, Map.class);
            //获取规格信息
            String specIds = goodsCategoryBrandSpec.getSpecIds();
            List<Map> specList = JSON.parseArray(specIds, Map.class);
            for (Map specMap : specList) {
                Integer specId = (Integer) specMap.get("id");
                //根据specId查询option的查询条件
                GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
                goodsSpecOptionExample.createCriteria().andSpecIdEqualTo(new Long(specId));
                List<GoodsSpecOption> goodsSpecOptions = goodsSpecOptionMapper.selectByExample(goodsSpecOptionExample);
                specMap.put("optionList", goodsSpecOptions);
            }
            resultMap.put("brandList", brandList);
            resultMap.put("specList", specList);

        }

    }


}

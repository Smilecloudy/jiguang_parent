package com.it.yanxuan.solr;

/**
 * @auther: cyb
 * @create: 2020/7/21 10:27
 */

import com.it.yanxuan.mapper.GoodsSpuMapper;
import com.it.yanxuan.model.GoodsSpu;
import com.it.yanxuan.model.GoodsSpuExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import java.util.List;

/**
 * 商品信息从MySQL中查询出来保存到solr中
 */
public class GoodsImportService {
    @Autowired
    private GoodsSpuMapper goodsSpuMapper;
    @Autowired
    private SolrTemplate solrTemplate;

    /*
        查询所有商品的信息
     */
    private List<GoodsSpu> queryAll() {
        //创建查询条件
        GoodsSpuExample goodsSpuExample = new GoodsSpuExample();
        //查询审核通过的商品
        //goodsSpuExample.createCriteria().andStatusEqualTo("1");
        List<GoodsSpu> goodsSpuList = goodsSpuMapper.selectByExample(goodsSpuExample);
        return goodsSpuList;
    }

    /*
        将查询的商品信息保存到solr
     */
    public void importToSolr() {
        List<GoodsSpu> goodsSpus = this.queryAll();
        /**
         * 特别注意：solrTemplate.saveBeans和solrTemplate.saveBean如果使用了后者，将会报
         * Document is missing mandatory uniqueKey field: id; nested exception is org.a
         */
        solrTemplate.saveBeans(goodsSpus);
        solrTemplate.commit();
    }

}

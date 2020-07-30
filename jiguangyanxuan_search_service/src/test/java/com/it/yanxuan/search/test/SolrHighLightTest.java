package com.it.yanxuan.search.test;

import com.it.yanxuan.model.GoodsSpu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @auther: cyb
 * @create: 2020/7/29 15:09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-solr.xml")
public class SolrHighLightTest {
    @Autowired
    private SolrTemplate solrTemplate;
    @Test
    public void testHLQuery() throws Exception{
        Criteria criteria = new Criteria("goods_keywords").contains("手机");
        //创建查询条件
        SimpleHighlightQuery shq = new SimpleHighlightQuery(criteria);
        //创建高亮显示操作
        HighlightOptions options = new HighlightOptions();
        options.addField("goods_label");
        //设置高亮前缀
        options.setSimplePrefix("<em style='color;red;'>");
        options.setSimplePostfix("</em>");
        //将高亮options加入到查询条件
        shq.setHighlightOptions(options);
        //高亮查询
        HighlightPage<GoodsSpu> highlightPage = solrTemplate.queryForHighlightPage(shq, GoodsSpu.class);
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
            System.out.println(entity);
        }
    }
}

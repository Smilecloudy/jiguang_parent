package com.it.yanxuan.search.test;

import com.it.yanxuan.model.GoodsSpu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @auther: cyb
 * @create: 2020/7/29 16:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-solr.xml")
public class SolrGroupTest {
    @Autowired
    private SolrTemplate solrTemplate;

    @Test
    public void testGroupQuery() throws Exception{
        //创建查询条件
        Criteria criteria = new Criteria("goods_keywords").is("华为");
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
            System.out.println(groupValue);
        }

    }

}

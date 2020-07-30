package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.goods.api.IGoodsSpecService;
import com.it.yanxuan.mapper.GoodsSpecMapper;
import com.it.yanxuan.mapper.GoodsSpecOptionMapper;
import com.it.yanxuan.model.GoodsSpec;
import com.it.yanxuan.model.GoodsSpecExample;
import com.it.yanxuan.model.GoodsSpecOption;
import com.it.yanxuan.model.GoodsSpecOptionExample;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.Specification;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/27 12:41
 */
@Service
@Transactional
public class GoodsSpecServiceImpl implements IGoodsSpecService {

    @Autowired
    private GoodsSpecMapper goodsSpecMapper;

    @Autowired
    private GoodsSpecOptionMapper goodsSpecOptionMapper;

    //根据条件进行分页查询
    @Override
    public PageResult<GoodsSpec> pageQuery(Integer currentPage, Integer pageSize, GoodsSpec goodsSpec) {
        //创建一个Example
        GoodsSpecExample goodsSpecExample = new GoodsSpecExample();

        if (goodsSpec != null) {
            if (goodsSpec.getName() != null && !"".equals(goodsSpec.getName())) {
                //如果传入一个goodsSpec的name，就得进行模糊查询
                goodsSpecExample.createCriteria().andNameLike("%" + goodsSpec.getName() + "%");
            }
        }

        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        //查询
        Page<GoodsSpec> pageData = (Page<GoodsSpec>) goodsSpecMapper.selectByExample(goodsSpecExample);

        //构建PageResult<GoodsSpec>作为返回结果
        PageResult<GoodsSpec> goodsSpecPageResult = new PageResult<>();
        goodsSpecPageResult.setTotal(pageData.getTotal());
        goodsSpecPageResult.setResult(pageData.getResult());

        return goodsSpecPageResult;
    }

    //保存规格信息
    @Override
    public int save(Specification specification) {
        //设置状态为有效
        specification.setStatus("0");
        //返回保存规格信息的影响行数
        int result = goodsSpecMapper.insert(specification);
        //保存规格项信息
        for (GoodsSpecOption option : specification.getOptionList()) {
            option.setStatus("0");
            option.setSpecId(specification.getId());
            goodsSpecOptionMapper.insertSelective(option);
        }

        return result;
    }

    //根据id查询规格信息
    @Override
    public Specification selectById(Long id) {
        //查询规格信息
        GoodsSpec goodsSpec = goodsSpecMapper.selectByPrimaryKey(id);
        //查询规格项
        GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
        goodsSpecOptionExample.createCriteria().andSpecIdEqualTo(id);

        //查询
        List<GoodsSpecOption> goodsSpecOptions = goodsSpecOptionMapper.selectByExample(goodsSpecOptionExample);

        //构建返回结果
        Specification specification = new Specification();
        BeanUtils.copyProperties(goodsSpec,specification);
        specification.setOptionList(goodsSpecOptions);
        return specification;
    }


    @Override
    public int update(Specification specification) {
        //更新规格信息
        int update = goodsSpecMapper.updateByPrimaryKeySelective(specification);
        //根据specId的值，先删除再保存
        GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
        goodsSpecOptionExample.createCriteria().andSpecIdEqualTo(specification.getId());
        goodsSpecOptionMapper.deleteByExample(goodsSpecOptionExample);
        //保存规格项信息
        for (GoodsSpecOption goodsSpecOption : specification.getOptionList()) {
            goodsSpecOption.setStatus("0");
            goodsSpecOption.setSpecId(specification.getId());
            goodsSpecOptionMapper.insertSelective(goodsSpecOption);
        }
        return update;
    }

    //根据id删除规格信息
    @Override
    public int delete(Long id) {
        GoodsSpec goodsSpec = new GoodsSpec();
        goodsSpec.setId(id);
        goodsSpec.setStatus("1");
        //根据id更新规格信息
        int update = goodsSpecMapper.updateByPrimaryKeySelective(goodsSpec);
        //规格项信息
        GoodsSpecOptionExample goodsSpecOptionExample = new GoodsSpecOptionExample();
        //更新的内容
        GoodsSpecOption goodsSpecOption = new GoodsSpecOption();
        goodsSpecOption.setStatus("1");
        int result = goodsSpecOptionMapper.updateByExampleSelective(goodsSpecOption, goodsSpecOptionExample);

        if (update > 0 && result > 0) {
            return update + result;
        }
        return 0;
    }


}

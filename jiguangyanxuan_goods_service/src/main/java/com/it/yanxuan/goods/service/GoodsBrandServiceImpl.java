package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.goods.api.IGoodsBrandService;
import com.it.yanxuan.mapper.GoodsBrandMapper;
import com.it.yanxuan.model.GoodsBrand;
import com.it.yanxuan.model.GoodsBrandExample;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-06-2020/6/23 13:47
 */
@Service
@Transactional
public class GoodsBrandServiceImpl implements IGoodsBrandService {


    //注入mapper映射接口DAO
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;

    //查询所有商品品牌
    @Override
    public List<GoodsBrand> queryAll() {
        return goodsBrandMapper.selectByExample(null);
    }

    //根据分页参数进行分页查询
    @Override
    public PageResult<GoodsBrand> pageQuery(Integer currentPage, Integer pageSize) {
        //开启分页
        PageHelper.startPage(currentPage, pageSize);

        Page<GoodsBrand> goodsBrands = (Page<GoodsBrand>) goodsBrandMapper.selectByExample(null);

        //构建返回结果
        PageResult<GoodsBrand> pageResult = new PageResult<>();
        pageResult.setTotal(goodsBrands.getTotal());
        pageResult.setResult(goodsBrands.getResult());

        return pageResult;
    }

    //根据查询条件、分页参数进行条件分页查询
    @Override
    public PageResult<GoodsBrand> pageQuery(Integer currentPage, Integer pageSize, GoodsBrand goodsBrand) {
        GoodsBrandExample goodsBrandExample = new GoodsBrandExample();
        //根据中文名称进行模糊查询
        if (goodsBrand != null && goodsBrand.getName() != null && !"".equals(goodsBrand.getName())) {
            goodsBrandExample.createCriteria().andNameLike("%" + goodsBrand.getName() + "%");
        }

        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        Page<GoodsBrand> goodsBrands = (Page<GoodsBrand>) goodsBrandMapper.selectByExample(goodsBrandExample);
        //构建返回结果
        PageResult<GoodsBrand> pageResult = new PageResult<>();
        pageResult.setTotal(goodsBrands.getTotal());
        pageResult.setResult(goodsBrands.getResult());

        return pageResult;
    }

    //将新增的品牌信息保存到数据库
    @Override
    public int save(GoodsBrand goodsBrand) {
        //进行新增商品品牌信息的时候，将isDelete设置为0，即正常
        goodsBrand.setIsDelete("0");
        int result = goodsBrandMapper.insert(goodsBrand);
        return result;
    }

    //将修改的品牌信息保存到数据库
    @Override
    public int update(GoodsBrand goodsBrand) {
        int result = goodsBrandMapper.updateByPrimaryKeySelective(goodsBrand);
        return result;
    }

    //根据品牌的id从数据库删除品牌信息
    @Override
    public int deleteByBrandId(Long id) {
        //物理删除
        //goodsBrandMapper.deleteByPrimaryKey(id);

        //逻辑删除
        GoodsBrand goodsBrand = new GoodsBrand();
        goodsBrand.setId(id);
        goodsBrand.setIsDelete("1");
        int result = goodsBrandMapper.updateByPrimaryKeySelective(goodsBrand);
        return result;
    }

    //通过id查询品牌信息
    @Override
    public GoodsBrand queryById(Long id) {
        GoodsBrand goodsBrand = goodsBrandMapper.selectByPrimaryKey(id);
        return goodsBrand;
    }
}

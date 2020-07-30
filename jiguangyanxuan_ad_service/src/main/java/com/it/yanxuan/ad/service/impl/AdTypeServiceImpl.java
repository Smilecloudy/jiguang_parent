package com.it.yanxuan.ad.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.ad.api.IAdTypeService;
import com.it.yanxuan.mapper.AdTypeMapper;
import com.it.yanxuan.model.AdType;
import com.it.yanxuan.model.AdTypeExample;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther: cyb
 * @create: 2020/7/17 17:28
 */
@Service
@Transactional
public class AdTypeServiceImpl implements IAdTypeService {

    @Autowired
    private AdTypeMapper adTypeMapper;

    @Override
    public PageResult<AdType> query(Integer currentPage, Integer pageSize, AdType adType) {
        AdTypeExample adTypeExample = new AdTypeExample();
        if (adType != null) {
            if (adType.getName() != null && !"".equals(adType.getName())) {
                adTypeExample.createCriteria().andNameLike(adType.getName());
            }
        }
        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        //查询
        Page<AdType> pageData = (Page<AdType>) adTypeMapper.selectByExample(adTypeExample);
        //构建返回结果
        PageResult<AdType> pageResult = new PageResult<>();
        pageResult.setResult(pageData.getResult());
        pageResult.setTotal(pageData.getTotal());
        return pageResult;
    }

    @Override
    public int save(AdType adType) {
        return adTypeMapper.insert(adType);
    }

    @Override
    public int update(AdType adType) {
        return adTypeMapper.updateByPrimaryKey(adType);
    }

    @Override
    public int delete(Long id) {
        return adTypeMapper.deleteByPrimaryKey(id);
    }
}

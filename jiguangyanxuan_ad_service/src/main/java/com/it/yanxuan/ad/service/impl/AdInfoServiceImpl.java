package com.it.yanxuan.ad.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.ad.api.IAdInfoService;
import com.it.yanxuan.mapper.AdInfoMapper;
import com.it.yanxuan.model.AdInfo;
import com.it.yanxuan.model.AdInfoExample;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @auther: cyb
 * @create: 2020/7/24 12:06
 */
@Service
public class AdInfoServiceImpl implements IAdInfoService {
    @Autowired
    private AdInfoMapper adInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public PageResult<AdInfo> pageQuery(Integer currentPage, Integer pageSize, AdInfo adInfo) {
        if (adInfo != null && adInfo.getTypeId() != null && !"".equals(adInfo.getTypeId())) {
             PageResult pageResult = (PageResult) redisTemplate.boundHashOps("adInfo").get(adInfo.getTypeId().toString());
            if (pageResult != null) {
                System.out.println("从redis获取adInfo");
                return pageResult;
            }
        }
        //构建查询条件
        AdInfoExample adInfoExample = new AdInfoExample();
        AdInfoExample.Criteria criteria = adInfoExample.createCriteria();
        if (adInfo != null) {
            //模糊查询
            if (adInfo.getName() != null && !"".equals(adInfo.getName())) {
                criteria.andNameLike("%" + adInfo.getName() + "%");
            }
            //广告类型
            if (adInfo.getTypeId() != null && !"".equals(adInfo.getTypeId())) {
                criteria.andTypeIdEqualTo(adInfo.getTypeId());
            }
        }
        //开启分页查询
        PageHelper.startPage(currentPage, pageSize);
        //查询
        com.github.pagehelper.Page<AdInfo> pageData = (com.github.pagehelper.Page<AdInfo>) adInfoMapper.selectByExampleWithBLOBs(adInfoExample);
        PageResult<AdInfo> pageResult = new PageResult<>();
        pageResult.setTotal(pageData.getTotal());
        pageResult.setResult(pageData.getResult());
        //执行到这里的话，就代表Redis中没有，就从数据库中进行查询
        if (adInfo != null && adInfo.getTypeId() != null && !"".equals(adInfo.getTypeId())) {
            redisTemplate.boundHashOps("adInfo").put(adInfo.getTypeId().toString(), pageResult);
        }
        return pageResult;
    }

    @Override
    public int save(AdInfo adInfo) {
        //清空缓存
        redisTemplate.boundHashOps("adInfo").delete(adInfo.getTypeId().toString());
        return adInfoMapper.insert(adInfo);
    }

    @Override
    public int update(AdInfo adInfo) {
        redisTemplate.boundHashOps("adInfo").delete(adInfo.getTypeId().toString());
        return adInfoMapper.updateByPrimaryKeyWithBLOBs(adInfo);
    }

    @Override
    public int deleteById(Long id) {
        AdInfo adInfo = adInfoMapper.selectByPrimaryKey(id);
        redisTemplate.boundHashOps("adInfo").delete(adInfo.getTypeId().toString());
        AdInfo adInfo1 = new AdInfo();
        adInfo1.setId(id);
        adInfo1.setStatus("1");
        return adInfoMapper.updateByPrimaryKeySelective(adInfo1);
    }

    @Override
    public AdInfo queryById(Long id) {
        return adInfoMapper.selectByPrimaryKey(id);
    }
}

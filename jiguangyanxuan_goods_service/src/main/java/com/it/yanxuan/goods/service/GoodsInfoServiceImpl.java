package com.it.yanxuan.goods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.goods.api.IGoodsInfoService;
import com.it.yanxuan.mapper.*;
import com.it.yanxuan.model.*;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.GoodsInfo;
import com.it.yanxuan.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/8 14:45
 */
@Service
@Transactional
public class GoodsInfoServiceImpl implements IGoodsInfoService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SellerShopMapper sellerShopMapper;
    @Autowired
    private GoodsSpuMapper goodsSpuMapper;
    @Autowired
    private GoodsSkuMapper goodsSkuMapper;
    @Autowired
    private GoodsBrandMapper goodsBrandMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;


    @Override
    public PageResult<GoodsSpu> pageQuery(Integer currentPage, Integer pageSize, GoodsSpu goodsSpu) {
        //创建查询条件
        GoodsSpuExample goodsSpuExample = new GoodsSpuExample();
        GoodsSpuExample.Criteria criteria = goodsSpuExample.createCriteria();
        if (goodsSpu.getName() != null && !"".equals(goodsSpu.getName())) {
            criteria.andNameLike("%" + goodsSpu.getName() + "%");
        }
        //根据商品的状态进行查询
        if (goodsSpu.getStatus() != null && !"".equals(goodsSpu.getStatus())) {
            criteria.andStatusEqualTo(goodsSpu.getStatus());
        }
        //获取当前店铺的信息，createPerson所关联的店铺信息
        if (goodsSpu.getCreatePerson() != null && !"".equals(goodsSpu.getCreatePerson())) {
            SellerShop sellerShop = this.querySellerShop(goodsSpu.getCreatePerson());
            //根据店铺的id进行查询
            if (sellerShop != null) {
                criteria.andSellerIdEqualTo(sellerShop.getId());
            }
        }
        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        Page<GoodsSpu> pageData = (Page<GoodsSpu>) goodsSpuMapper.selectByExample(goodsSpuExample);
        //构建返回结果
        PageResult<GoodsSpu> pageResult = new PageResult<>();
        pageResult.setTotal(pageData.getTotal());
        pageResult.setResult(pageData.getResult());
        return pageResult;
    }

    //完成商品的审核
    @Override
    public int audit(GoodsSpu goodsSpu) {
        //设置spu的信息
        int update = goodsSpuMapper.updateByPrimaryKeySelective(goodsSpu);
        //设置sku
        GoodsSkuExample goodsSkuExample = new GoodsSkuExample();
        goodsSkuExample.createCriteria().andGoodsIdEqualTo(goodsSpu.getId());
        //创建出更新的内容
        GoodsSku goodsSku = new GoodsSku();
        goodsSku.setStatus(goodsSpu.getStatus());
        goodsSkuMapper.updateByExampleSelective(goodsSku,goodsSkuExample);
        return update;
    }

    //
    @Override
    public int update(GoodsInfo goodsInfo) {
        if (!goodsInfo.getLabel().contains("[")) {
            //【商品的品牌】商品的名称、标题
            String label = "["+goodsInfo.getBrandName()+"] "+goodsInfo.getName()+" "+goodsInfo.getLabel();
            goodsInfo.setLabel(label);
        }
        //根据id更新spu信息
        int update = goodsSpuMapper.updateByPrimaryKey(goodsInfo);
        //删除当前商品关联的sku信息
        GoodsSkuExample goodsSkuExample = new GoodsSkuExample();
        goodsSkuExample.createCriteria().andGoodsIdEqualTo(goodsInfo.getId());
        goodsSkuMapper.deleteByExample(goodsSkuExample);
        this.saveSkuList(goodsInfo);
        return update;
    }

    //保存spu关联的商品信息
    private void saveSkuList(GoodsInfo goodsInfo) {
        //获取SKU信息
        List<GoodsSku> skuList = goodsInfo.getSkuList();
        for (GoodsSku sku : skuList) {
            //设置goodsId
            sku.setGoodsId(goodsInfo.getId());
            //设置标题
            sku.setLabel(goodsInfo.getLabel());
            //设置品牌的名称
            sku.setBrandName(goodsInfo.getBrandName());
            //设置类目的名称
            sku.setCategoryName(goodsInfo.getCategoryName());
            //设置商铺信息
            sku.setSellerId(goodsInfo.getSellerId());
            sku.setSellerName(goodsInfo.getSellerName());
            //设置状态
            sku.setOnSale("0");
            sku.setStatus("0");
            goodsSkuMapper.insert(sku);
        }
    }

    //根据id查询商品信息
    @Override
    public GoodsInfo queryById(Long id) {
        //先查询SPU信息
        GoodsSpu goodsSpu = goodsSpuMapper.selectByPrimaryKey(id);
        //查询SKU信息
        GoodsSkuExample goodsSkuExample = new GoodsSkuExample();
        goodsSkuExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsSku> skuList = goodsSkuMapper.selectByExample(goodsSkuExample);
        //构建返回结果
        GoodsInfo goodsinfo = new GoodsInfo();
        BeanUtils.copyProperties(goodsSpu,goodsinfo);
        goodsinfo.setSkuList(skuList);
        return goodsinfo;
    }

    private SellerShop querySellerShop(String username) {
        //根据登陆人的用户名取得店铺的信息
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andLoginNameEqualTo(username);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        Account account = null;
        if (accountList != null && accountList.size() > 0) {
            account = accountList.get(0);
        }
        //查询店铺的信息
        SellerShopExample sellerShopExample = new SellerShopExample();
        sellerShopExample.createCriteria().andAccountIdEqualTo(account.getId());
        List<SellerShop> shopList = sellerShopMapper.selectByExample(sellerShopExample);
        SellerShop sellerShop = null;
        if (shopList != null && shopList.size() > 0) {
            sellerShop = shopList.get(0);
        }
        return sellerShop;
    }

    @Override
    public int save(GoodsInfo goodsInfo) {
        //根据createPerson查询相关联的店铺信息
        SellerShop sellerShop = this.querySellerShop(goodsInfo.getCreatePerson());
        //设置商铺的信息
        goodsInfo.setSellerId(sellerShop.getId());
        goodsInfo.setSellerName(sellerShop.getName());
        goodsInfo.setStatus("0");
        //查询品牌的名称
        GoodsBrand goodsBrand = goodsBrandMapper.selectByPrimaryKey(goodsInfo.getBrandId());
        goodsInfo.setBrandName(goodsBrand.getName());
        //查询类目信息
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goodsInfo.getCategory3Id());
        goodsInfo.setCategoryName(goodsCategory.getStructName()+">"+goodsCategory.getName());
        //[商品的品牌]商品的名称、标题
        String label = "["+goodsInfo.getBrandName()+"] "+goodsInfo.getName()+" "+goodsInfo.getLabel();
        goodsInfo.setLabel(label);
        //完成商品的保存
        int result = goodsSpuMapper.insert(goodsInfo);
        this.saveSkuList(goodsInfo);
        return result;
    }

    //删除
    @Override
    public int delete(Long id) {
        GoodsSpu goodsSpu = new GoodsSpu();
        goodsSpu.setId(id);
        goodsSpu.setStatus("3");
        int delete = goodsSpuMapper.updateByPrimaryKeySelective(goodsSpu);
        return delete;
    }

    @Override
    public int putOnShelf(GoodsInfo goodsInfo) {
        if (goodsInfo.getOnSale() != null && !"".equals(goodsInfo.getOnSale())) {
            int result = goodsSpuMapper.updateByPrimaryKeySelective(goodsInfo);
            return result;
        }
        return 0;
    }
}

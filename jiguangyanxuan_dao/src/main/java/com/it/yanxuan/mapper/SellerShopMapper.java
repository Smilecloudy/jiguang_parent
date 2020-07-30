package com.it.yanxuan.mapper;

import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.model.SellerShopExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellerShopMapper {
    long countByExample(SellerShopExample example);

    int deleteByExample(SellerShopExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SellerShop record);

    int insertSelective(SellerShop record);

    List<SellerShop> selectByExample(SellerShopExample example);

    SellerShop selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SellerShop record, @Param("example") SellerShopExample example);

    int updateByExample(@Param("record") SellerShop record, @Param("example") SellerShopExample example);

    int updateByPrimaryKeySelective(SellerShop record);

    int updateByPrimaryKey(SellerShop record);
}
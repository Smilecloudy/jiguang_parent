package com.it.yanxuan.mapper;

import com.it.yanxuan.model.GoodsCategoryBrandSpec;
import com.it.yanxuan.model.GoodsCategoryBrandSpecExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsCategoryBrandSpecMapper {
    long countByExample(GoodsCategoryBrandSpecExample example);

    int deleteByExample(GoodsCategoryBrandSpecExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GoodsCategoryBrandSpec record);

    int insertSelective(GoodsCategoryBrandSpec record);

    List<GoodsCategoryBrandSpec> selectByExample(GoodsCategoryBrandSpecExample example);

    GoodsCategoryBrandSpec selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GoodsCategoryBrandSpec record, @Param("example") GoodsCategoryBrandSpecExample example);

    int updateByExample(@Param("record") GoodsCategoryBrandSpec record, @Param("example") GoodsCategoryBrandSpecExample example);

    int updateByPrimaryKeySelective(GoodsCategoryBrandSpec record);

    int updateByPrimaryKey(GoodsCategoryBrandSpec record);
}
package com.it.yanxuan.mapper;

import com.it.yanxuan.model.GoodsSpecOption;
import com.it.yanxuan.model.GoodsSpecOptionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsSpecOptionMapper {
    long countByExample(GoodsSpecOptionExample example);

    int deleteByExample(GoodsSpecOptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(GoodsSpecOption record);

    int insertSelective(GoodsSpecOption record);

    List<GoodsSpecOption> selectByExample(GoodsSpecOptionExample example);

    GoodsSpecOption selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GoodsSpecOption record, @Param("example") GoodsSpecOptionExample example);

    int updateByExample(@Param("record") GoodsSpecOption record, @Param("example") GoodsSpecOptionExample example);

    int updateByPrimaryKeySelective(GoodsSpecOption record);

    int updateByPrimaryKey(GoodsSpecOption record);
}
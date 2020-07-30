package com.it.yanxuan.mapper;

import com.it.yanxuan.model.AdType;
import com.it.yanxuan.model.AdTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdTypeMapper {
    long countByExample(AdTypeExample example);

    int deleteByExample(AdTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AdType record);

    int insertSelective(AdType record);

    List<AdType> selectByExample(AdTypeExample example);

    AdType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AdType record, @Param("example") AdTypeExample example);

    int updateByExample(@Param("record") AdType record, @Param("example") AdTypeExample example);

    int updateByPrimaryKeySelective(AdType record);

    int updateByPrimaryKey(AdType record);
}
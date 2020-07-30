package com.it.yanxuan.mapper;

import com.it.yanxuan.model.SecondKillOrder;
import com.it.yanxuan.model.SecondKillOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SecondKillOrderMapper {
    long countByExample(SecondKillOrderExample example);

    int deleteByExample(SecondKillOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SecondKillOrder record);

    int insertSelective(SecondKillOrder record);

    List<SecondKillOrder> selectByExample(SecondKillOrderExample example);

    SecondKillOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SecondKillOrder record, @Param("example") SecondKillOrderExample example);

    int updateByExample(@Param("record") SecondKillOrder record, @Param("example") SecondKillOrderExample example);

    int updateByPrimaryKeySelective(SecondKillOrder record);

    int updateByPrimaryKey(SecondKillOrder record);
}
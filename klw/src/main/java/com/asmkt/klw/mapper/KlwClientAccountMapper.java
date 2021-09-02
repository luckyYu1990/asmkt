package com.asmkt.klw.mapper;

import com.asmkt.klw.bean.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface KlwClientAccountMapper {

    @Select("Select * from ClientAccount where id = #{id}")
    Activity selectById(Long id);

    @Update("Update ClientAccount set CurrentBalance = #{number} where id = #{id}")
    void updateCurrentBalanceById(Long id, int number);
}

package com.asmkt.klw.mapper;

import com.asmkt.klw.bean.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KlwActivityMapper {

    @Select("Select * from Activity where id = #{id}")
    Activity selectById(Long id);
}

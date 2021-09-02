package com.asmkt.klw.mapper;

import com.asmkt.klw.bean.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface KlwActivityGoodMapper {

    @Select("Select * from ActivityGoods where id = #{id}")
    Good selectById(Long id);

    @Update("Update ActivityGoods set CanUseStock = #{number} where Id = #{goodId}")
    void updateGoodCanStock(long goodId, int number);
}

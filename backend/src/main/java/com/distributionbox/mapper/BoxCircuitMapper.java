package com.distributionbox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.distributionbox.entity.BoxCircuit;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoxCircuitMapper extends BaseMapper<BoxCircuit> {

    @Select("select * from sys_box_circuit where box_id = #{boxId} order by id desc")
    List<BoxCircuit> byBoxId(Integer boxId);

    @Delete("delete from sys_box_circuit where box_id = #{boxId}")
    int deleteByBoxId(Integer boxId);
}

package com.distributionbox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.distributionbox.entity.EmergencyRepairBox;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmergencyRepairBoxMapper extends BaseMapper<EmergencyRepairBox> {

    @Delete("delete from sys_emergency_repair_box where emergency_repair_id = #{repairId}")
    int deleteByRepairId(Integer repairId);

    @Select("select box_id from sys_emergency_repair_box where emergency_repair_id = #{repairId} order by box_id")
    List<Integer> selectBoxIdsByRepairId(Integer repairId);

    @Select("select distinct emergency_repair_id from sys_emergency_repair_box where box_id = #{boxId}")
    List<Integer> selectRepairIdsByBoxId(Integer boxId);
}

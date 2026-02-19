package com.distributionbox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.distributionbox.entity.InspectionBox;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InspectionBoxMapper extends BaseMapper<InspectionBox> {

    @Delete("delete from sys_inspection_box where inspection_id = #{inspectionId}")
    int deleteByInspectionId(Integer inspectionId);

    @Select("select box_id from sys_inspection_box where inspection_id = #{inspectionId} order by box_id")
    List<Integer> selectBoxIdsByInspectionId(Integer inspectionId);

    @Select("select distinct inspection_id from sys_inspection_box where box_id = #{boxId}")
    List<Integer> selectInspectionIdsByBoxId(Integer boxId);
}

package com.distributionbox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.distributionbox.controller.dto.InspectionItemViewDto;
import com.distributionbox.entity.InspectionItem;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InspectionItemMapper extends BaseMapper<InspectionItem> {

    @Delete("delete from sys_maintenance_item where task_id = #{taskId}")
    int deleteByTaskId(Integer taskId);

    @Select("select box_id from sys_maintenance_item where task_id = #{taskId} order by box_id")
    List<Integer> selectBoxIdsByTaskId(Integer taskId);

    @Select("select distinct task_id from sys_maintenance_item where box_id = #{boxId}")
    List<Integer> selectTaskIdsByBoxId(Integer boxId);

    @Select("""
        select i.*, t.task_no, t.inspection_user, t.guardian_user, t.inspection_time, t.remark as task_remark
        from sys_maintenance_item i
        left join sys_maintenance_task t on t.id = i.task_id
        where i.box_id = #{boxId}
        order by t.inspection_time desc, i.id desc
        limit #{offset}, #{pageSize}
        """)
    List<InspectionItemViewDto> selectItemHistoryByBoxId(@Param("boxId") Integer boxId,
                                                         @Param("offset") Integer offset,
                                                         @Param("pageSize") Integer pageSize);

    @Select("""
        select count(1)
        from sys_maintenance_item i
        where i.box_id = #{boxId}
        """)
    Long countItemHistoryByBoxId(@Param("boxId") Integer boxId);
}

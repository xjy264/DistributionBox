package com.distributionbox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.distributionbox.controller.dto.RepairItemViewDto;
import com.distributionbox.entity.RepairItem;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RepairItemMapper extends BaseMapper<RepairItem> {

    @Delete("delete from sys_emergency_repair_item where task_id = #{taskId}")
    int deleteByTaskId(Integer taskId);

    @Select("select box_id from sys_emergency_repair_item where task_id = #{taskId} order by box_id")
    List<Integer> selectBoxIdsByTaskId(Integer taskId);

    @Select("""
        select i.*, t.task_no, t.report_user, t.report_time, t.fix_user, t.fix_time, t.remark as task_remark
        from sys_emergency_repair_item i
        left join sys_emergency_repair_task t on t.id = i.task_id
        where i.box_id = #{boxId}
        order by t.fix_time desc, t.report_time desc, i.id desc
        limit #{offset}, #{pageSize}
        """)
    List<RepairItemViewDto> selectItemHistoryByBoxId(@Param("boxId") Integer boxId,
                                                     @Param("offset") Integer offset,
                                                     @Param("pageSize") Integer pageSize);

    @Select("""
        select count(1)
        from sys_emergency_repair_item i
        where i.box_id = #{boxId}
        """)
    Long countItemHistoryByBoxId(@Param("boxId") Integer boxId);

    @Select("select * from sys_emergency_repair_item where task_id = #{taskId} order by id asc")
    List<RepairItem> selectByTaskId(Integer taskId);
}

package com.distributionbox.mapper;

import com.distributionbox.entity.Components;
import com.distributionbox.entity.Inspection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjy
 * @since 2024-07-06
 */
@Mapper
public interface InspectionMapper extends BaseMapper<Inspection> {

    @Select("select * from sys_inspection where `pid`=#{id} ;")
    List<Inspection> byInspectionId(String id);
}

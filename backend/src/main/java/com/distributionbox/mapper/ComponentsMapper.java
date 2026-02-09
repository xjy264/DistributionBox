package com.distributionbox.mapper;

import com.distributionbox.entity.Components;
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
 * @since 2024-04-09
 */
@Mapper
public interface ComponentsMapper extends BaseMapper<Components> {

    @Select("select * from sys_components where `box_id`=#{id} ;")
    List<Components> byBoxId(Integer id);
}

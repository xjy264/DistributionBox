package com.distributionbox.mapper;

import com.distributionbox.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjy
 * @since 2022-07-28
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("SELECT id from sys_role where identity = #{role}")
    Integer selectByIdentity(String role);
}

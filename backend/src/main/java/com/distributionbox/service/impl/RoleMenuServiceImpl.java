package com.distributionbox.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.entity.RoleMenu;
import com.distributionbox.mapper.RoleMenuMapper;
import com.distributionbox.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2022-07-14
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
}

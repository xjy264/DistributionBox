package com.xjy.shujia.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjy.shujia.common.Constants;
import com.xjy.shujia.controller.dto.UserDto;
import com.xjy.shujia.entity.Menu;
import com.xjy.shujia.entity.User;
import com.xjy.shujia.exception.ServiceException;
import com.xjy.shujia.mapper.RoleMapper;
import com.xjy.shujia.mapper.RoleMenuMapper;
import com.xjy.shujia.mapper.UserMapper;
import com.xjy.shujia.service.IMenuService;
import com.xjy.shujia.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjy.shujia.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG =Log.get();

    @Resource
    private RoleMapper roleMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    IMenuService menuService;

    @Override
    public UserDto login(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        queryWrapper.eq("password",userDto.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (one != null){
            BeanUtils.copyProperties(one,userDto);
            String token = JwtUtils.createToken(one.getId().toString(), one.getPassword());
            userDto.setToken(token);

            String role = one.getRole();
            List<Menu> roleMenus = getRoleMenus(role);
            userDto.setMenus(roleMenus);
            return userDto;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public User register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        queryWrapper.eq("password",user.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (one == null){
            return user;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
    }

    private List<Menu> getRoleMenus(String identity){
        Integer roleId = roleMapper.selectByIdentity(identity);
        List<Integer> roleIds = roleMenuMapper.selectByRoleId(roleId);
        //查出系统所有的菜单
        List<Menu> menus = menuService.findMenus("");
        List<Menu> roleMenus = new ArrayList<>();
        //筛选当前用户的菜单
        for (Menu menu : menus) {
            if (roleIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //移除children中不在roleIds里面的child.id元素
            children.removeIf(child -> !roleIds.contains(child.getId()));
        }

        return roleMenus;
    }
}

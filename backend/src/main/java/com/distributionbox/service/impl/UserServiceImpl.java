package com.distributionbox.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.distributionbox.common.Constants;
import com.distributionbox.controller.dto.UserDto;
import com.distributionbox.entity.Menu;
import com.distributionbox.entity.User;
import com.distributionbox.exception.ServiceException;
import com.distributionbox.mapper.RoleMapper;
import com.distributionbox.mapper.RoleMenuMapper;
import com.distributionbox.mapper.UserMapper;
import com.distributionbox.service.IMenuService;
import com.distributionbox.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.distributionbox.security.JwtService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Resource
    private RoleMapper roleMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    IMenuService menuService;

    public UserServiceImpl(JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto login(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDto.getUsername());
        User one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (one != null && userDto.getPassword().equals(one.getPassword())){
            BeanUtils.copyProperties(one,userDto);
            String token = jwtService.generateToken(one.getUsername(), java.util.Map.of(
                    "userId", one.getId(),
                    "role", one.getRole() == null ? "" : one.getRole()
            ));
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

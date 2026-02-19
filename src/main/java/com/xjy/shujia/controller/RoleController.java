package com.xjy.shujia.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Menu;
import com.xjy.shujia.service.IMenuService;
import com.xjy.shujia.service.IRoleMenuService;
import com.xjy.shujia.service.impl.MenuServiceImpl;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IRoleService;
import com.xjy.shujia.entity.Role;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2022-07-28
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    IMenuService menuService;

    @PostMapping
    public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.list()) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (!name.equals("")){
            queryWrapper.like("name",name);
        }
        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper)) ;
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(roleService.removeBatchByIds(ids)) ;
    }

    /**
     * 绑定角色和菜单的关系
     * @param roleId 角色Id
     * @param menuIds 菜单Id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @GetMapping("/checks/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return Result.success(roleService.getRoleMenu(roleId));
    }
}

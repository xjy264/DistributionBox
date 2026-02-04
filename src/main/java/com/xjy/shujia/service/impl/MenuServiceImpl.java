package com.xjy.shujia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjy.shujia.entity.Menu;
import com.xjy.shujia.mapper.MenuMapper;
import com.xjy.shujia.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2022-07-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (!name.equals("")){
            queryWrapper.like("name",name);
        }
        // 查询所有数据
        List<Menu> list = list(queryWrapper);
        // 找出pid为null的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (Menu menu : parentNodes) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            wrapper.eq("pid",menu.getId());
            menu.setChildren(list(wrapper));
        }

        return parentNodes;
    }
}

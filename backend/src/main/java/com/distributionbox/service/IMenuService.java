package com.distributionbox.service;

import com.distributionbox.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjy
 * @since 2022-07-28
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}

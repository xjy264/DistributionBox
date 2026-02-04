package com.xjy.shujia.service;

import com.xjy.shujia.entity.Components;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjy
 * @since 2024-04-09
 */
public interface IComponentsService extends IService<Components> {

    List<Components> byBoxId(Integer id);
}

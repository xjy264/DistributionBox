package com.xjy.shujia.service;

import com.xjy.shujia.entity.Inspection;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjy
 * @since 2024-07-06
 */
public interface IInspectionService extends IService<Inspection> {

    List<Inspection> byInspectionId(String id);
}

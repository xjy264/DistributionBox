package com.distributionbox.service.impl;

import com.distributionbox.entity.Department;
import com.distributionbox.mapper.DepartmentMapper;
import com.distributionbox.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2022-12-17
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}

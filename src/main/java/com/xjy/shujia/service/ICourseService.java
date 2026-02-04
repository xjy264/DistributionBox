package com.xjy.shujia.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjy
 * @since 2022-09-05
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> page, String name);

    List<Course> findTeacherCourses(Integer id);

    Boolean selectCourse(Integer studentId, Integer courseId);

    List<Course> findStudentCourses(Integer id);
}

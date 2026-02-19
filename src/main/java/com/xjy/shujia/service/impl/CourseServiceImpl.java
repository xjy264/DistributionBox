package com.xjy.shujia.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Course;
import com.xjy.shujia.mapper.CourseMapper;
import com.xjy.shujia.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2022-09-05
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    CourseMapper courseMapper;

    @Override
    public Page<Course> findPage(Page<Course> page, String name) {
        return courseMapper.findPage(page,name);
    }

    @Override
    public List<Course> findTeacherCourses(Integer id) {
        return courseMapper.findTeacherCourses(id);
    }

    @Override
    public Boolean selectCourse(Integer studentId, Integer courseId) {
        courseMapper.deleteCourse(studentId,courseId);
        return courseMapper.selectCourse(studentId,courseId);
    }

    @Override
    public List<Course> findStudentCourses(Integer id) {
        return courseMapper.findStudentCourses(id);
    }
}

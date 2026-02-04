package com.xjy.shujia.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjy
 * @since 2022-09-05
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    Page<Course> findPage(Page<Course> page,@Param("name") String name);

    List<Course> findTeacherCourses(@Param("id") Integer id);

    Boolean selectCourse(@Param("studentId")Integer studentId,@Param("courseId") Integer courseId);

    void deleteCourse(@Param("studentId")Integer studentId,@Param("courseId") Integer courseId);

    List<Course> findStudentCourses(@Param("id")Integer id);
}

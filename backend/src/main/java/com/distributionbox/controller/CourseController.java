package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import com.distributionbox.common.Result;

import com.distributionbox.service.ICourseService;
import com.distributionbox.entity.Course;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2022-09-05
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @PostMapping
    public Result save(@RequestBody Course course) {
        return Result.success(courseService.saveOrUpdate(course)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(courseService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(courseService.list()) ;
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(courseService.list()) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "")String name) {
//        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
//        if (!name.equals("")){
//            queryWrapper.like("name",name);
//        }
//        return Result.success(courseService.findPage(new Page<>(pageNum, pageSize),queryWrapper)) ;
        Page<Course> page = courseService.findPage(new Page<>(pageNum,pageSize),name);
        return Result.success(page);
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(courseService.removeBatchByIds(ids)) ;
    }

    @GetMapping("/find/teacherCourses/{id}")
    public Result findTeacherCourses(@PathVariable Integer id){
        List<Course> course = courseService.findTeacherCourses(id);
        return Result.success(course);
    }

    @GetMapping("/selectCourse")
    public Result selectCourse(@RequestParam Integer studentId,@RequestParam Integer courseId){
        Boolean flag = courseService.selectCourse(studentId,courseId);
        return Result.success(flag);
    }

    @GetMapping("/find/studentCourses/{id}")
    public Result findStudentCourses(@PathVariable Integer id){
        List<Course> course = courseService.findStudentCourses(id);
        return Result.success(course);
    }
}

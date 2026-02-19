package com.xjy.shujia.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IHomeService;
import com.xjy.shujia.entity.Home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-04-08
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private IHomeService homeService;

    //查找全部
    @GetMapping
    public List<Home> index(){
        return homeService.list();
    }

    @GetMapping("/{id}")
    public Home getById(@PathVariable Integer id){
        return homeService.getById(id);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Home home){
        return homeService.saveOrUpdate(home);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return homeService.removeById(id);
    }

//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
//        pageNum = (pageNum -1) * pageSize;
//        List<Box> data = boxMapper.selectPage(pageNum, pageSize);
//        Integer total = boxMapper.selectTotal();
//        Map<String,Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//    }

    @GetMapping("/page")
    public IPage<Home> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String id,
                                @RequestParam(defaultValue = "") String station,
                                @RequestParam(defaultValue = "") String area,
                                @RequestParam(defaultValue = "") String user){
        IPage<Home> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Home> queryWrapper = new QueryWrapper<>();
        if (!"".equals(id)) {
            queryWrapper.like("id", id);
        }
        if (!"".equals(station)) {
            queryWrapper.like("station", station);
        }
        if (!"".equals(area)) {
            queryWrapper.like("area", area);
        }
        if (!"".equals(user)) {
            queryWrapper.like("user", user);
        }
        queryWrapper.orderByDesc("id");
        return homeService.page(page, queryWrapper);
    }

    /**
     * 导出接口
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Home> list = homeService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("bill", "报销单据");
        writer.addHeaderAlias("remark", "备注");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

    }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<Home> homes = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Home home = new Home();
            home.setStation(row.get(1).toString());
            home.setRemark(row.get(2).toString());
            homes.add(home);
        }

        homeService.saveBatch(homes);
        return true;
    }
}

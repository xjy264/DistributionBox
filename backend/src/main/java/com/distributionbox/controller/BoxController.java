package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.distributionbox.entity.Components;
import com.distributionbox.service.IComponentsService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.distributionbox.service.IBoxService;
import com.distributionbox.entity.Box;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2024-04-09
 */
@RestController
@RequestMapping("/box")
public class BoxController {

    @Resource
    private IBoxService boxService;

    @Resource
    private IComponentsService componentsService;

    //查找全部
    @GetMapping
    public List<Box> index() {
        return boxService.list();
    }

    @GetMapping("/{id}")
    public Box getById(@PathVariable Integer id) {
        return boxService.getById(id);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Box box) {
        return boxService.saveOrUpdate(box);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return boxService.removeById(id);
    }

    @PostMapping("/saveById/{id}")
    public boolean saveById(@PathVariable Integer id) {
        Box box = new Box();
        box.setId(id);
        return boxService.saveOrUpdate(box);
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
    public IPage<Box> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String id,
                               @RequestParam(defaultValue = "") String address,
                               @RequestParam(defaultValue = "") String area) {
        IPage<Box> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Box> queryWrapper = new QueryWrapper<>();
        if (!"".equals(id)) {
            queryWrapper.like("id", id);
        }
        if (!"".equals(address)) {
            queryWrapper.like("box_address", address);
        }
        if (!"".equals(area)) {
            queryWrapper.like("area", area);
        }
        queryWrapper.orderByDesc("id");
        return boxService.page(page, queryWrapper);
    }

    @GetMapping("/print/{id}")
    public ResponseEntity<InputStreamResource> generateWordFile(@PathVariable Integer id) throws IOException {
        Box box = boxService.getById(id);

//        String filepath = "F:/IntelliJ IDEA 2021.1.2/shujia-localhost-7/src/main/resources/words/template.docx";
        String filepath = "F:/Intel";
        HashMap<String, Object> data = new HashMap<>();
        data.put("station", box.getStation());
        data.put("area", box.getArea());
        data.put("boxAddress", box.getBoxAddress());
        data.put("boxId", box.getBoxId());
        data.put("size", box.getSize());

        List<Components> components = componentsService.byBoxId(id);

        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();

        Configure config = Configure.builder()
                .bind("components", policy).build();

        data.put("components",components);

     // 渲染模板
        XWPFTemplate template = XWPFTemplate.compile(filepath,config).render(data);

        // 创建输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            template.writeAndClose(baos); // 渲染并关闭 template
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (template != null) {
                template.close();  // 确保 template 已关闭
            }
        }

        // 创建输入流
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        // 设置响应头
        String fileName = URLEncoder.encode("配电箱信息", "UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + fileName + ".docx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        // 构建返回值
        ResponseEntity<InputStreamResource> responseEntity = new ResponseEntity<>(new InputStreamResource(bais), headers, HttpStatus.OK);

        // 关闭流
        try {
            baos.close();
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseEntity;
    }}


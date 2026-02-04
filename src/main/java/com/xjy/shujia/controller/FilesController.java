package com.xjy.shujia.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjy.shujia.common.Constants;
import com.xjy.shujia.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import com.xjy.shujia.common.Result;

import com.xjy.shujia.service.IFilesService;
import com.xjy.shujia.entity.Files;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjy
 * @since 2022-07-22
 */
@RestController
@RequestMapping("/files")
public class FilesController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private IFilesService filesService;

    @Resource
    FilesMapper filesMapper;

    /**
     * 文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String originalFilename = file.getOriginalFilename();
        String type = FileNameUtil.extName(originalFilename);//扩展名
        long size = file.getSize();

        //定义唯一的标识码
        String uuid = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + uuid);

        //判断文件目录是否存在，不存在创建一个新的文件目录
        if (!uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().mkdirs();
        }

        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileMd5(md5);
        if (dbFiles != null) { // 文件已存在
            url = dbFiles.getUrl();
            uuid = dbFiles.getUuid();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://39.107.69.103:8888/files/" + uuid;
        }

        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setUuid(uuid);
        saveFile.setMd5(md5);

        filesService.save(saveFile);
        return uuid;
    }

    @GetMapping("/{uuid}")
    public void downLoad(@PathVariable String uuid, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + uuid);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uuid, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过md5查询文件是否重复
     * @param md5
     * @return
     */
    private Files getFileMd5(String md5){
        //根据md5查询
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> list = filesService.list(queryWrapper);

        return list.size() == 0 ? null : list.get(0);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        return Result.success(filesMapper.updateById(files)) ;
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(filesService.removeById(id)) ;
    }

    @GetMapping
    public Result findAll() {
        return Result.success(filesService.list()) ;
    }

    @GetMapping("/query/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(filesService.getById(id)) ;
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        if (!name.equals("")){
            queryWrapper.eq("name",name);
        }
        return Result.success(filesService.page(new Page<>(pageNum, pageSize),queryWrapper)) ;
    }

    @PostMapping("/del/batch")
    public Result BatchDelete(@RequestBody List<Integer> ids){
        return Result.success(filesService.removeBatchByIds(ids)) ;
    }
}

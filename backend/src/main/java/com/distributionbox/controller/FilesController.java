package com.distributionbox.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.common.Constants;
import com.distributionbox.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.distributionbox.common.Result;

import com.distributionbox.service.IFilesService;
import com.distributionbox.entity.Files;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FilesController {
    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp", "gif", "bmp");

    @Value("${app.files.upload-path}")
    private String fileUploadPath;

    @Value("${app.files.base-url}")
    private String baseUrl;

    @Value("${app.files.image-max-size-bytes:10485760}")
    private long imageMaxSizeBytes;

    private static final String FILES_PATH_PREFIX = "/files/";

    @Resource
    private IFilesService filesService;

    @Resource
    FilesMapper filesMapper;

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
        return uploadImage(file);
    }

    @PostMapping("/image/upload")
    public Result uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return Result.error(Constants.CODE_400, "上传失败，请选择图片文件");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = FileNameUtil.extName(originalFilename).toLowerCase();
        if (StrUtil.isBlank(extension) || !ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            return Result.error(Constants.CODE_400, "仅支持 jpg/jpeg/png/webp/gif/bmp 图片格式");
        }

        String contentType = file.getContentType();
        if (StrUtil.isBlank(contentType) || !contentType.toLowerCase().startsWith("image/")) {
            return Result.error(Constants.CODE_400, "非法文件类型，仅允许上传图片");
        }

        long size = file.getSize();
        if (size <= 0 || size > imageMaxSizeBytes) {
            return Result.error(Constants.CODE_400, "图片大小超限，最大允许 " + (imageMaxSizeBytes / 1024 / 1024) + "MB");
        }

        String uuid = IdUtil.fastSimpleUUID() + StrUtil.DOT + extension;
        File uploadFile = new File(fileUploadPath, uuid);
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        String md5 = SecureUtil.md5(file.getInputStream());
        Files dbFiles = getFileMd5(md5);
        String storedPath;
        if (dbFiles != null) {
            uuid = dbFiles.getUuid();
            storedPath = FILES_PATH_PREFIX + uuid;
        } else {
            file.transferTo(uploadFile);
            storedPath = FILES_PATH_PREFIX + uuid;
        }

        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(extension);
        saveFile.setSize(size);
        saveFile.setUrl(storedPath);
        saveFile.setUuid(uuid);
        saveFile.setMd5(md5);
        filesService.save(saveFile);

        Map<String, Object> payload = new HashMap<>();
        payload.put("path", storedPath);
        payload.put("url", storedPath);
        payload.put("accessUrl", normalizeBaseUrl(baseUrl) + uuid);
        payload.put("filename", originalFilename);
        payload.put("size", size);
        payload.put("contentType", contentType);
        payload.put("extension", extension);
        payload.put("uuid", uuid);
        payload.put("previewUrl", FILES_PATH_PREFIX + "preview/" + uuid);

        return Result.success(payload);
    }

    @GetMapping("/{uuid}")
    public void downLoad(@PathVariable String uuid, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath, uuid);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(uuid, StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    @GetMapping("/preview/{uuid}")
    public void preview(@PathVariable String uuid, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath, uuid);
        ServletOutputStream os = response.getOutputStream();
        String mimeType = FileUtil.getMimeType(uuid);
        response.setContentType(StrUtil.isBlank(mimeType) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType);
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    private String normalizeBaseUrl(String url) {
        if (StrUtil.isBlank(url)) {
            return FILES_PATH_PREFIX;
        }
        return url.endsWith("/") ? url : url + "/";
    }

    private Files getFileMd5(String md5){
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

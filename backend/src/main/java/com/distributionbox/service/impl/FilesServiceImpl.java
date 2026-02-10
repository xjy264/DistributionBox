package com.distributionbox.service.impl;

import com.distributionbox.entity.Files;
import com.distributionbox.mapper.FilesMapper;
import com.distributionbox.service.IFilesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjy
 * @since 2022-07-22
 */
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

}

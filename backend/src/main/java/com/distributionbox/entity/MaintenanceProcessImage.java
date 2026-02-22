package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_maintenance_process_image")
public class MaintenanceProcessImage {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer taskId;
    private String imageUrl;
}

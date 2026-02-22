package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_maintenance_compare_image")
public class MaintenanceCompareImage {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer taskId;
    private String diseaseLocation;
    private String beforeImageUrl;
    private String diseaseNote;
    private String afterImageUrl;
    private String disposalNote;
}

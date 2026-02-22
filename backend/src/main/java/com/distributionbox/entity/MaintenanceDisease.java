package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_maintenance_disease")
public class MaintenanceDisease {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer taskId;
    private Integer seqNo;
    private String diseaseLocation;
    private String diseaseDesc;
    private Integer quantity;
    private String disposalMethod;
    private String remark;
}

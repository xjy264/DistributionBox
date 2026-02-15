package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_location")
public class Location {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer level;

    private Integer sortNum;
}

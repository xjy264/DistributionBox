package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sys_dict")
public class Dict {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String value;
    private String type;
    private Integer sort;
    private LocalDateTime createTime;
}

package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjy
 * @since 2024-07-15
 */
@Getter
@Setter
  @TableName("sys_check")
@ApiModel(value = "Check对象", description = "")
public class Check implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String pid;

    private String content;

    private String state;

    private String remark;


}

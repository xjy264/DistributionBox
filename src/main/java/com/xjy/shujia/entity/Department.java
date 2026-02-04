package com.xjy.shujia.entity;

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
 * @since 2022-12-17
 */
@Getter
@Setter
  @TableName("sys_department")
@ApiModel(value = "Department对象", description = "")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty("部门")
      private String department;

      @ApiModelProperty("地址")
      private String address;


}

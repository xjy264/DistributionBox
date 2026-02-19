package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Department对象")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @Schema(description = "部门")
      private String department;

      @Schema(description = "地址")
      private String address;


}

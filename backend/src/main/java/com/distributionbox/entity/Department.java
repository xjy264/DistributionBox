package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      private String department;

      private String address;


}

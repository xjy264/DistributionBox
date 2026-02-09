package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjy
 * @since 2022-07-28
 */
@Getter
@Setter
  @TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String name;

      private String path;

      private String icon;

      private String description;

      @TableField(exist = false)
      private List<Menu> children;

      private Integer pid;

      private String pagePath;

      private String hideMenu;
}

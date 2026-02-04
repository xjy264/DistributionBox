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
 * @since 2024-04-08
 */
@Getter
@Setter
  @TableName("sys_home")
@ApiModel(value = "Home对象", description = "")
public class Home implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String station;

    private String address;

    private String remark;

    private String user;

    private String area;

    private String boxId;

    private String size;
}

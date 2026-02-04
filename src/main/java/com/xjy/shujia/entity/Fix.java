package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2024-05-17
 */
@Getter
@Setter
  @TableName("sys_fix")
@ApiModel(value = "Fix对象", description = "")
public class Fix implements Serializable {

    private static final long serialVersionUID = 1L;

  @ApiModelProperty("ID")
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

      @ApiModelProperty("设备编码")
      private String code;

      @ApiModelProperty("巡检时间")
      private LocalDateTime time;

      @ApiModelProperty("安装地点")
      private String address;

      @ApiModelProperty("巡检结果")
      private String result;

      @ApiModelProperty("巡检员")
      private String user;

      @ApiModelProperty("备注")
      private String remark;

      private Integer boxId;
}

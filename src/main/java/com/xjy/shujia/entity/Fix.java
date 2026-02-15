package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2024-05-17
 */
@Getter
@Setter
  @TableName("sys_fix")
@Schema(name = "Fix对象")
public class Fix implements Serializable {

    private static final long serialVersionUID = 1L;

  @Schema(description = "ID")
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

      @Schema(description = "设备编码")
      private String code;

      @Schema(description = "巡检时间")
      private LocalDateTime time;

      @Schema(description = "安装地点")
      private String address;

      @Schema(description = "巡检结果")
      private String result;

      @Schema(description = "巡检员")
      private String user;

      @Schema(description = "备注")
      private String remark;

      private Integer boxId;
}

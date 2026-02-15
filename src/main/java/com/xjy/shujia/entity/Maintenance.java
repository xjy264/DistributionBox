package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjy
 * @since 2024-06-26
 */
@Getter
@Setter
  @TableName("sys_maintenance")
@Schema(name = "Maintenance对象")
public class Maintenance implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String pid;

    private String reportUser;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") //后端注解格式化日期
    private Date reportTime;

    private String fixUser;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") //后端注解格式化日期
    private Date fixTime;

    private String components;

    private String faultPhenomenon;

    private String faultReason;

    private String fixProcess;

    private String remark;

    private String firstUrl;

    private String secondUrl;

    private String thirdUrl;

    private String fourthUrl;


}

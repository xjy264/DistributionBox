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
 * @since 2024-07-06
 */
@Getter
@Setter
  @TableName("sys_inspection")
@Schema(name = "Inspection对象")
public class Inspection implements Serializable {

    private static final long serialVersionUID = 1L;

      @Schema(description = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String pid;

      @Schema(description = "巡检人员")
      private String inspectionUser;

      @Schema(description = "监护人员")
      private String  guardianUser;

      @Schema(description = "巡检时间")
      private LocalDateTime inspectionTime;

      @Schema(description = "巡检图片一")
      private String firstUrl;

      @Schema(description = "巡检图片二")
      private String secondUrl;

      @Schema(description = "巡检图片三")
      private String thirdUrl;

      @Schema(description = "巡检图片四")
      private String fourthUrl;

      @Schema(description = "巡检图片五")
      private String fifthUrl;

      private String appearanceUrl;

      @Schema(description = "维保前图片")
      private String earlierUrl;

      @Schema(description = "维保后图片")
      private String laterUrl;

      private String switchModel;

      private String currentRating;

      private String traverseSection;

      private String supplyVoltage;

      private String aStarting;

      private String aRunning;

      private String bStarting;

      private String bRunning;

      private String cStarting;

      private String cRunning;

      private String remark;
}

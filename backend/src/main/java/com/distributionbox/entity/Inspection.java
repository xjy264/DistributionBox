package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Inspection implements Serializable {

    private static final long serialVersionUID = 1L;

        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String pid;

      private String inspectionUser;

      private String  guardianUser;

      private LocalDateTime inspectionTime;

      private String firstUrl;

      private String secondUrl;

      private String thirdUrl;

      private String fourthUrl;

      private String fifthUrl;

      private String appearanceUrl;

      private String earlierUrl;

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

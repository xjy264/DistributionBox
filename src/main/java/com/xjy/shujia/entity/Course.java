package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-09-05
 */
@Getter
@Setter
  @Schema(name = "Course对象")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

      @Schema(description = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @Schema(description = "课程名称")
      private String name;

      @Schema(description = "学分")
      private Integer score;

      @Schema(description = "上课时间")
      private String times;

      @Schema(description = "是否开课")
      private Boolean state;

      @Schema(description = "授课教师id")
      private Integer teacherId;

      @TableField(exist = false)
      private String teacher;
}

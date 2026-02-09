package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String name;

      private Integer score;

      private String times;

      private Boolean state;

      private Integer teacherId;

      @TableField(exist = false)
      private String teacher;
}

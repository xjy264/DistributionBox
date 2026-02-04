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
 * @since 2024-04-09
 */
@Getter
@Setter
  @TableName("sys_box")
@ApiModel(value = "Box对象", description = "")
public class Box implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String boxAddress;

    private String boxId;

    private String station;

    private String area;

    private String systemUrl;

    private String firstUrl;

    private String secondUrl;

    private String thirdUrl;

    private String fourthUrl;

    private String size;
}

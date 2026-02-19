package com.xjy.shujia.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2024-04-09
 */
@Getter
@Setter
  @TableName("sys_components")
@Schema(name = "Components对象")
public class Components implements Serializable {

    private static final long serialVersionUID = 1L;

      @Schema(description = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String componentsName;

    private String componentsUnit;

    private String componentsQuantity;

    private String componentsType;

    private String componentsState;

    private String boxId;

    private String controlScope;

    private String inSpecifications;

    private String outSpecifications;
}

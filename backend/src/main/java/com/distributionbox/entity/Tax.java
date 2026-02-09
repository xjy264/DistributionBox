package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjy
 * @since 2022-12-17
 */
@Getter
@Setter
  @TableName("sys_tax")
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String pre;

    private String after;

    private String rate;

    private String fund;
}

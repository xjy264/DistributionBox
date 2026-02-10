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
 * @since 2024-07-16
 */
@Getter
@Setter
  @TableName("sys_record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private String terminalName;

    private String temperature;

    private String runningCurrent;

    private String startingCurrent;

    private String leakage;

    private String voltage;

    private String insulation;

    private String ground;

    private String remark;

    private String number;

    private String pid;


}

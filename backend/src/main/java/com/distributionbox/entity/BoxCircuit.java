package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_box_circuit")
public class BoxCircuit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer boxId;

    private String supplyCircuit;





    private String startCurrent;

    private String runCurrent;

    private String power;

    private String electricDevice;

    private String deviceLocation;

    private String remark;
}

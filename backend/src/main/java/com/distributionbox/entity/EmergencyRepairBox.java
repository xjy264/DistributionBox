package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_emergency_repair_box")
public class EmergencyRepairBox implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer emergencyRepairId;

    private Integer boxId;
}

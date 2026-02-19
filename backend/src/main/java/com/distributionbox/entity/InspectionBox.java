package com.distributionbox.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("sys_inspection_box")
public class InspectionBox implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer inspectionId;

    private Integer boxId;
}

package com.distributionbox.controller.dto;

import com.distributionbox.entity.RepairItem;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairTaskSaveDto {

    private Integer id;
    private String taskNo;
    private String reportUser;
    private Date reportTime;
    private String fixUser;
    private Date fixTime;
    private String remark;
    private List<RepairItem> items;
}

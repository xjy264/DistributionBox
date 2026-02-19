package com.distributionbox.controller.dto;

import com.distributionbox.entity.RepairItem;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairItemViewDto extends RepairItem {

    private String taskNo;
    private String reportUser;
    private Date reportTime;
    private String fixUser;
    private Date fixTime;
    private String taskRemark;
}

package com.distributionbox.controller.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairTaskSaveDto {

    private Integer id;
    private String taskNo;
    private Integer boxId;
    private String reportUnit;
    private String reportUser;
    private Date reportTime;
    private String acceptUser;
    private String supervisionUser;
    private String rescueUsers;
    private String faultPhenomenon;
    private String faultReason;
    private String rescueSituation;
}

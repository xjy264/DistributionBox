package com.distributionbox.controller.dto;

import com.distributionbox.entity.RepairTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairTaskViewDto extends RepairTask {
    private String boxAccount;
}

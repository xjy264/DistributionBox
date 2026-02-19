package com.distributionbox.controller.dto;

import com.distributionbox.entity.RepairItem;
import com.distributionbox.entity.RepairTask;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairTaskViewDto extends RepairTask {

    private Integer boxCount;
    private List<Integer> boxIds;
    private List<RepairItem> items;
    private String faultPhenomenon;
}

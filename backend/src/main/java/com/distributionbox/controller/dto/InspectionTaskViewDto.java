package com.distributionbox.controller.dto;

import com.distributionbox.entity.InspectionItem;
import com.distributionbox.entity.InspectionTask;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionTaskViewDto extends InspectionTask {

    private Integer boxCount;
    private List<Integer> boxIds;
    private List<String> boxAccounts;
    private List<InspectionItem> items;
}

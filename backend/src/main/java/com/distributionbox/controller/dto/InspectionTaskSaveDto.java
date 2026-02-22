package com.distributionbox.controller.dto;

import com.distributionbox.entity.InspectionItem;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionTaskSaveDto {

    private Integer id;
    private String taskNo;
    private String inspectionUser;
    private String guardianUser;
    private LocalDateTime inspectionTime;
    private String maintenanceType;
    private String maintenanceLocation;
    private String maintenanceContent;
    private String maintenanceSummary;
    private String remark;
    private List<InspectionItem> items;
}

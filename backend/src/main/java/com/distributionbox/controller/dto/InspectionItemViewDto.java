package com.distributionbox.controller.dto;

import com.distributionbox.entity.InspectionItem;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionItemViewDto extends InspectionItem {

    private String taskNo;
    private String inspectionUser;
    private String guardianUser;
    private LocalDateTime inspectionTime;
    private String taskRemark;
}

package com.distributionbox.controller.dto;

import com.distributionbox.entity.EmergencyRepair;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyRepairViewDto extends EmergencyRepair {

    private List<Integer> boxIds;
}

package com.distributionbox.controller.dto;

import com.distributionbox.entity.Inspection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionSaveDto extends Inspection {

    private List<Integer> boxIds;
}

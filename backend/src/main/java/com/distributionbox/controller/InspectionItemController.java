package com.distributionbox.controller;

import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.InspectionItemViewDto;
import com.distributionbox.mapper.InspectionItemMapper;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inspection-item")
public class InspectionItemController {

    @Resource
    private InspectionItemMapper inspectionItemMapper;

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam Integer boxId) {
        int offset = (pageNum - 1) * pageSize;
        List<InspectionItemViewDto> records = inspectionItemMapper.selectItemHistoryByBoxId(boxId, offset, pageSize);
        Long total = inspectionItemMapper.countItemHistoryByBoxId(boxId);
        return Result.success(Map.of(
                "records", records,
                "total", total,
                "current", pageNum,
                "size", pageSize
        ));
    }
}

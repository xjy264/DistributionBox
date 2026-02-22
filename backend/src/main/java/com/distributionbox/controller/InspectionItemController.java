package com.distributionbox.controller;

import com.distributionbox.common.Result;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/maintenance-item", "/inspection-item"})
public class InspectionItemController {

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(required = false) Integer boxId) {
        return Result.success(Map.of(
                "records", List.of(),
                "total", 0,
                "current", pageNum,
                "size", pageSize
        ));
    }
}

package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.EmergencyRepairSaveDto;
import com.distributionbox.controller.dto.EmergencyRepairViewDto;
import com.distributionbox.entity.EmergencyRepair;
import com.distributionbox.entity.EmergencyRepairBox;
import com.distributionbox.mapper.EmergencyRepairBoxMapper;
import com.distributionbox.service.IEmergencyRepairService;
import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emergency-repair")
public class EmergencyRepairController {

    @Resource
    private IEmergencyRepairService emergencyRepairService;

    @Resource
    private EmergencyRepairBoxMapper emergencyRepairBoxMapper;

    @PostMapping("/save")
    public Result save(@RequestBody EmergencyRepairSaveDto dto) {
        EmergencyRepair repair = new EmergencyRepair();
        BeanUtils.copyProperties(dto, repair);
        emergencyRepairService.saveOrUpdate(repair);

        emergencyRepairBoxMapper.deleteByRepairId(repair.getId());
        saveRelations(repair.getId(), dto.getBoxIds());
        return Result.success(true);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        emergencyRepairBoxMapper.deleteByRepairId(id);
        return Result.success(emergencyRepairService.removeById(id));
    }

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String faultPhenomenon,
                       @RequestParam(required = false) Integer boxId) {
        QueryWrapper<EmergencyRepair> queryWrapper = new QueryWrapper<>();
        if (!faultPhenomenon.isBlank()) {
            queryWrapper.like("fault_phenomenon", faultPhenomenon.trim());
        }

        if (boxId != null) {
            List<Integer> repairIds = emergencyRepairBoxMapper.selectRepairIdsByBoxId(boxId);
            if (repairIds.isEmpty()) {
                return Result.success(Map.of(
                        "records", Collections.emptyList(),
                        "total", 0,
                        "current", pageNum,
                        "size", pageSize
                ));
            }
            queryWrapper.in("id", repairIds);
        }

        queryWrapper.orderByDesc("id");
        IPage<EmergencyRepair> page = emergencyRepairService.page(new Page<>(pageNum, pageSize), queryWrapper);

        List<EmergencyRepairViewDto> records = page.getRecords().stream().map(row -> {
            EmergencyRepairViewDto view = new EmergencyRepairViewDto();
            BeanUtils.copyProperties(row, view);
            view.setBoxIds(emergencyRepairBoxMapper.selectBoxIdsByRepairId(row.getId()));
            return view;
        }).collect(Collectors.toList());

        return Result.success(Map.of(
                "records", records,
                "total", page.getTotal(),
                "current", page.getCurrent(),
                "size", page.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        EmergencyRepair repair = emergencyRepairService.getById(id);
        if (repair == null) {
            return Result.success(null);
        }
        EmergencyRepairViewDto view = new EmergencyRepairViewDto();
        BeanUtils.copyProperties(repair, view);
        view.setBoxIds(emergencyRepairBoxMapper.selectBoxIdsByRepairId(id));
        return Result.success(view);
    }

    private void saveRelations(Integer repairId, List<Integer> boxIds) {
        if (boxIds == null || boxIds.isEmpty()) {
            return;
        }
        Set<Integer> unique = boxIds.stream()
                .filter(id -> id != null && id > 0)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (Integer boxId : unique) {
            EmergencyRepairBox relation = new EmergencyRepairBox();
            relation.setEmergencyRepairId(repairId);
            relation.setBoxId(boxId);
            emergencyRepairBoxMapper.insert(relation);
        }
    }
}

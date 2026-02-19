package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.InspectionSaveDto;
import com.distributionbox.controller.dto.InspectionViewDto;
import com.distributionbox.entity.Inspection;
import com.distributionbox.entity.InspectionBox;
import com.distributionbox.mapper.InspectionBoxMapper;
import com.distributionbox.service.IInspectionService;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
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
@RequestMapping("/inspection")
public class InspectionController {

    @Resource
    private IInspectionService inspectionService;

    @Resource
    private InspectionBoxMapper inspectionBoxMapper;

    @GetMapping
    public Result findAll() {
        return Result.success(inspectionService.list());
    }

    @GetMapping("/getByPid/{pid}")
    public Inspection getByPid(@PathVariable String pid) {
        Integer boxId = parseBoxId(pid);
        if (boxId == null) {
            return null;
        }
        List<Integer> inspectionIds = inspectionBoxMapper.selectInspectionIdsByBoxId(boxId);
        if (inspectionIds.isEmpty()) {
            return null;
        }
        return inspectionService.getById(inspectionIds.get(0));
    }

    @GetMapping("/getList/{id}")
    public List<Inspection> getList(@PathVariable String id) {
        Integer boxId = parseBoxId(id);
        if (boxId == null) {
            return Collections.emptyList();
        }
        List<Integer> inspectionIds = inspectionBoxMapper.selectInspectionIdsByBoxId(boxId);
        if (inspectionIds.isEmpty()) {
            return Collections.emptyList();
        }
        QueryWrapper<Inspection> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", inspectionIds).orderByDesc("inspection_time");
        return inspectionService.list(queryWrapper);
    }

    @PostMapping("/save")
    public Result save(@RequestBody InspectionSaveDto dto) {
        Inspection inspection = new Inspection();
        BeanUtils.copyProperties(dto, inspection);
        inspectionService.saveOrUpdate(inspection);

        inspectionBoxMapper.deleteByInspectionId(inspection.getId());
        saveRelations(inspection.getId(), dto.getBoxIds());
        return Result.success(true);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        inspectionBoxMapper.deleteByInspectionId(id);
        return Result.success(inspectionService.removeById(id));
    }

    @PostMapping("/saveByPid/{pid}")
    public Result saveByPid(@PathVariable String pid) {
        Integer boxId = parseBoxId(pid);
        if (boxId == null) {
            return Result.error("400", "boxId参数无效");
        }

        Inspection inspection = new Inspection();
        inspection.setInspectionTime(LocalDateTime.now());
        inspectionService.save(inspection);

        InspectionBox relation = new InspectionBox();
        relation.setInspectionId(inspection.getId());
        relation.setBoxId(boxId);
        inspectionBoxMapper.insert(relation);
        return Result.success(true);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String inspectionUser,
                           @RequestParam(required = false) Integer boxId) {
        QueryWrapper<Inspection> queryWrapper = new QueryWrapper<>();
        if (!inspectionUser.isBlank()) {
            queryWrapper.like("inspection_user", inspectionUser.trim());
        }

        if (boxId != null) {
            List<Integer> inspectionIds = inspectionBoxMapper.selectInspectionIdsByBoxId(boxId);
            if (inspectionIds.isEmpty()) {
                return Result.success(Map.of(
                        "records", Collections.emptyList(),
                        "total", 0,
                        "current", pageNum,
                        "size", pageSize
                ));
            }
            queryWrapper.in("id", inspectionIds);
        }

        queryWrapper.orderByDesc("id");
        IPage<Inspection> page = inspectionService.page(new Page<>(pageNum, pageSize), queryWrapper);

        List<InspectionViewDto> records = page.getRecords().stream().map(row -> {
            InspectionViewDto view = new InspectionViewDto();
            BeanUtils.copyProperties(row, view);
            view.setBoxIds(inspectionBoxMapper.selectBoxIdsByInspectionId(row.getId()));
            return view;
        }).collect(Collectors.toList());

        return Result.success(Map.of(
                "records", records,
                "total", page.getTotal(),
                "current", page.getCurrent(),
                "size", page.getSize()
        ));
    }

    @PostMapping("/del/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            inspectionBoxMapper.deleteByInspectionId(id);
        }
        return Result.success(inspectionService.removeBatchByIds(ids));
    }

    private Integer parseBoxId(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private void saveRelations(Integer inspectionId, List<Integer> boxIds) {
        if (boxIds == null || boxIds.isEmpty()) {
            return;
        }
        Set<Integer> unique = boxIds.stream()
                .filter(id -> id != null && id > 0)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (Integer boxId : unique) {
            InspectionBox relation = new InspectionBox();
            relation.setInspectionId(inspectionId);
            relation.setBoxId(boxId);
            inspectionBoxMapper.insert(relation);
        }
    }
}

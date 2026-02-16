package com.distributionbox.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.distributionbox.common.Result;
import com.distributionbox.controller.dto.RepairTaskSaveDto;
import com.distributionbox.controller.dto.RepairTaskViewDto;
import com.distributionbox.entity.RepairItem;
import com.distributionbox.entity.RepairTask;
import com.distributionbox.mapper.RepairItemMapper;
import com.distributionbox.service.IRepairTaskService;
import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repair-task")
public class RepairTaskController {

    @Resource
    private IRepairTaskService repairTaskService;

    @Resource
    private RepairItemMapper repairItemMapper;

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public Result save(@RequestBody RepairTaskSaveDto dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            return Result.error("400", "items不能为空");
        }

        RepairTask task = new RepairTask();
        BeanUtils.copyProperties(dto, task);
        if (task.getTaskNo() == null || task.getTaskNo().isBlank()) {
            task.setTaskNo("RT" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        }
        repairTaskService.saveOrUpdate(task);

        repairItemMapper.deleteByTaskId(task.getId());
        List<RepairItem> validItems = deduplicateItems(dto.getItems());
        for (RepairItem item : validItems) {
            item.setId(null);
            item.setTaskId(task.getId());
            repairItemMapper.insert(item);
        }
        return Result.success(true);
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@PathVariable Integer id) {
        repairItemMapper.deleteByTaskId(id);
        return Result.success(repairTaskService.removeById(id));
    }

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String fixUser) {
        QueryWrapper<RepairTask> queryWrapper = new QueryWrapper<>();
        if (!fixUser.isBlank()) {
            queryWrapper.like("fix_user", fixUser.trim());
        }
        queryWrapper.orderByDesc("id");
        IPage<RepairTask> page = repairTaskService.page(new Page<>(pageNum, pageSize), queryWrapper);

        List<RepairTaskViewDto> records = page.getRecords().stream().map(task -> {
            RepairTaskViewDto view = new RepairTaskViewDto();
            BeanUtils.copyProperties(task, view);
            List<Integer> boxIds = repairItemMapper.selectBoxIdsByTaskId(task.getId());
            view.setBoxIds(boxIds);
            view.setBoxCount(boxIds.size());
            List<RepairItem> items = repairItemMapper.selectByTaskId(task.getId());
            if (!items.isEmpty()) {
                view.setFaultPhenomenon(items.get(0).getFaultPhenomenon());
            }
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
        RepairTask task = repairTaskService.getById(id);
        if (task == null) {
            return Result.success(null);
        }
        RepairTaskViewDto view = new RepairTaskViewDto();
        BeanUtils.copyProperties(task, view);

        List<RepairItem> items = repairItemMapper.selectByTaskId(id);
        view.setItems(items);
        List<Integer> boxIds = items.stream().map(RepairItem::getBoxId).toList();
        view.setBoxIds(boxIds);
        view.setBoxCount(boxIds.size());
        if (!items.isEmpty()) {
            view.setFaultPhenomenon(items.get(0).getFaultPhenomenon());
        }
        return Result.success(view);
    }

    private List<RepairItem> deduplicateItems(List<RepairItem> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Integer> seen = new LinkedHashSet<>();
        List<RepairItem> result = new ArrayList<>();
        for (RepairItem item : items) {
            if (item == null || item.getBoxId() == null || item.getBoxId() <= 0) {
                continue;
            }
            if (seen.add(item.getBoxId())) {
                result.add(item);
            }
        }
        return result;
    }
}
